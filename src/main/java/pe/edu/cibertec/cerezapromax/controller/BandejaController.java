package pe.edu.cibertec.cerezapromax.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pe.edu.cibertec.cerezapromax.model.Agente;
import pe.edu.cibertec.cerezapromax.model.Atencion;
import pe.edu.cibertec.cerezapromax.model.Campana;
import pe.edu.cibertec.cerezapromax.model.Cliente;
import pe.edu.cibertec.cerezapromax.model.Mensaje;
import pe.edu.cibertec.cerezapromax.model.Usuario;
import pe.edu.cibertec.cerezapromax.repository.AgenteRepository;
import pe.edu.cibertec.cerezapromax.repository.AtencionRepository;
import pe.edu.cibertec.cerezapromax.repository.CampanaRepository;
import pe.edu.cibertec.cerezapromax.repository.CategoriaRepository;
import pe.edu.cibertec.cerezapromax.repository.ClienteRepository;
import pe.edu.cibertec.cerezapromax.repository.MensajeRepository;
import pe.edu.cibertec.cerezapromax.service.AtencionService;

/**
 * Bandeja de atencion: el agente ve las conversaciones por cliente, el hilo de
 * mensajes y responde desde el mismo lugar. Cada respuesta se registra como una
 * atencion con sus mensajes mediante la transaccion de AtencionService.
 */
@Controller
@RequestMapping("/bandeja")
public class BandejaController {

	/** Fila de la lista de conversaciones. */
	public record Conversacion(Cliente cliente, Mensaje ultimo, long totalMensajes, long totalAtenciones) {}

	private final ClienteRepository clienteRepository;
	private final AgenteRepository agenteRepository;
	private final CategoriaRepository categoriaRepository;
	private final CampanaRepository campanaRepository;
	private final AtencionRepository atencionRepository;
	private final MensajeRepository mensajeRepository;
	private final AtencionService atencionService;

	public BandejaController(ClienteRepository clienteRepository, AgenteRepository agenteRepository,
			CategoriaRepository categoriaRepository, CampanaRepository campanaRepository,
			AtencionRepository atencionRepository, MensajeRepository mensajeRepository,
			AtencionService atencionService) {
		this.clienteRepository = clienteRepository;
		this.agenteRepository = agenteRepository;
		this.categoriaRepository = categoriaRepository;
		this.campanaRepository = campanaRepository;
		this.atencionRepository = atencionRepository;
		this.mensajeRepository = mensajeRepository;
		this.atencionService = atencionService;
	}

	@GetMapping
	public String bandeja(HttpSession session, @RequestParam(required = false) Integer idCliente, Model model) {
		if (!LoginController.permitido(session, "AGENTE", "ADMINISTRADOR", "SUPERVISOR")) {
			return "redirect:/login";
		}
		List<Conversacion> conversaciones = new ArrayList<>();
		for (Cliente c : clienteRepository.findByEstado("ACTIVO")) {
			conversaciones.add(new Conversacion(c,
					mensajeRepository.findFirstByAtencionClienteIdClienteOrderByIdMensajeDesc(c.getIdCliente()),
					mensajeRepository.countByAtencionClienteIdCliente(c.getIdCliente()),
					atencionRepository.countByClienteIdCliente(c.getIdCliente())));
		}
		// las conversaciones con actividad mas reciente primero
		conversaciones.sort((a, b) -> {
			int ia = a.ultimo() == null ? 0 : a.ultimo().getIdMensaje();
			int ib = b.ultimo() == null ? 0 : b.ultimo().getIdMensaje();
			return Integer.compare(ib, ia);
		});
		model.addAttribute("conversaciones", conversaciones);

		Cliente actual = null;
		if (idCliente != null) {
			actual = clienteRepository.findById(idCliente).orElse(null);
		} else if (!conversaciones.isEmpty()) {
			actual = conversaciones.get(0).cliente();
		}
		if (actual != null) {
			List<Atencion> hilo = atencionRepository.findByClienteIdClienteOrderByFechaAsc(actual.getIdCliente());
			model.addAttribute("cliente", actual);
			model.addAttribute("hilo", hilo);
			model.addAttribute("totalMensajes", mensajeRepository.countByAtencionClienteIdCliente(actual.getIdCliente()));
			model.addAttribute("duplicados", hilo.stream().flatMap(a -> a.getMensajes().stream())
					.filter(m -> "DUPLICADO".equals(m.getEstadoEnvio())).count());
		}
		model.addAttribute("categorias", categoriaRepository.findByEstado("ACTIVO"));
		model.addAttribute("campanas", campanaRepository.findAll());
		model.addAttribute("agente", agenteDeSesion(session));
		model.addAttribute("puedeResponder", !"SUPERVISOR".equals(session.getAttribute("rol")));
		model.addAttribute("plantillas", plantillas());
		return "bandeja";
	}

	@PostMapping("/enviar")
	public String enviar(HttpSession session, @RequestParam Integer idCliente, @RequestParam Integer idCategoria,
			@RequestParam(required = false) Integer idCampana, @RequestParam String contenido) {
		if (!LoginController.permitido(session, "AGENTE", "ADMINISTRADOR")) {
			return "redirect:/login";
		}
		Atencion atencion = new Atencion();
		atencion.setCliente(clienteRepository.findById(idCliente).orElseThrow());
		atencion.setAgente(agenteDeSesion(session));
		atencion.setCategoria(categoriaRepository.findById(idCategoria).orElseThrow());
		Campana campana = idCampana == null ? null : campanaRepository.findById(idCampana).orElse(null);
		// un mensaje por linea, se despachan en una sola transaccion
		List<String> mensajes = List.of(contenido.split("\\r?\\n"));
		atencionService.registrarAtencion(atencion, mensajes, campana);
		return "redirect:/bandeja?idCliente=" + idCliente + "&ok=enviado";
	}

	/** Cambia el estado de una atencion desde el hilo, sin enviar mensajes. */
	@PostMapping("/estado")
	public String estado(HttpSession session, @RequestParam Integer idAtencion, @RequestParam String estado,
			@RequestParam Integer idCliente) {
		if (!LoginController.permitido(session, "AGENTE", "ADMINISTRADOR")) {
			return "redirect:/login";
		}
		atencionRepository.findById(idAtencion).ifPresent(a -> {
			a.setEstado(estado);
			atencionRepository.save(a);
		});
		return "redirect:/bandeja?idCliente=" + idCliente + "&ok=estado";
	}

	/** Respuestas rapidas por categoria, para los chips del redactor. */
	private Map<String, List<String>> plantillas() {
		Map<String, List<String>> p = new LinkedHashMap<>();
		p.put("Reserva de servicio", List.of(
				"Su reserva fue confirmada. Le avisaremos cuando la unidad este en camino.",
				"Su unidad llega en 10 minutos. Por favor tenga su DNI a la mano.",
				"Lamentamos la demora, estamos reasignando una unidad cercana."));
		p.put("Reclamo", List.of(
				"Recibimos su reclamo y lo estamos revisando. Le respondemos en 24 horas.",
				"Su reclamo fue atendido. Gracias por su paciencia.",
				"Le devolvemos el cobro en un plazo de 48 horas."));
		p.put("Consulta de tarifas", List.of(
				"La tarifa al aeropuerto es de S/ 60.",
				"Las tarifas corporativas incluyen espera de 15 minutos sin costo.",
				"Puede solicitar una cotizacion para rutas fuera de Lima."));
		return p;
	}

	/** Agente vinculado al usuario de la sesion, o el primer agente activo si no tiene. */
	private Agente agenteDeSesion(HttpSession session) {
		Usuario u = (Usuario) session.getAttribute("usuario");
		Agente a = u == null ? null : agenteRepository.findFirstByUsuarioIdUsuario(u.getIdUsuario());
		if (a == null) {
			List<Agente> activos = agenteRepository.findByEstado("ACTIVO");
			a = activos.isEmpty() ? null : activos.get(0);
		}
		return a;
	}
}
