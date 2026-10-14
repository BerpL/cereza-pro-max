package pe.edu.cibertec.cerezapromax.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pe.edu.cibertec.cerezapromax.model.Atencion;
import pe.edu.cibertec.cerezapromax.model.Campana;
import pe.edu.cibertec.cerezapromax.repository.AgenteRepository;
import pe.edu.cibertec.cerezapromax.repository.CampanaRepository;
import pe.edu.cibertec.cerezapromax.repository.CategoriaRepository;
import pe.edu.cibertec.cerezapromax.repository.ClienteRepository;
import pe.edu.cibertec.cerezapromax.service.AtencionService;

@Controller
@RequestMapping("/atenciones")
public class AtencionController {

	private final AtencionService atencionService;
	private final ClienteRepository clienteRepository;
	private final AgenteRepository agenteRepository;
	private final CategoriaRepository categoriaRepository;
	private final CampanaRepository campanaRepository;

	public AtencionController(AtencionService atencionService, ClienteRepository clienteRepository,
			AgenteRepository agenteRepository, CategoriaRepository categoriaRepository,
			CampanaRepository campanaRepository) {
		this.atencionService = atencionService;
		this.clienteRepository = clienteRepository;
		this.agenteRepository = agenteRepository;
		this.categoriaRepository = categoriaRepository;
		this.campanaRepository = campanaRepository;
	}

	@GetMapping("/registro")
	public String registro(HttpSession session, Model model) {
		if (!LoginController.permitido(session, "AGENTE", "ADMINISTRADOR")) {
			return "redirect:/login";
		}
		model.addAttribute("clientes", clienteRepository.findByEstado("ACTIVO"));
		model.addAttribute("agentes", agenteRepository.findByEstado("ACTIVO"));
		model.addAttribute("categorias", categoriaRepository.findByEstado("ACTIVO"));
		model.addAttribute("campanas", campanaRepository.findAll());
		return "atenciones/registro";
	}

	// Caso transaccional: registrar atencion con envio de mensajes
	@PostMapping("/registrar")
	public String registrar(@RequestParam Integer idCliente, @RequestParam Integer idAgente,
			@RequestParam Integer idCategoria, @RequestParam(required = false) Integer idCampana,
			@RequestParam List<String> mensajes, Model model) {
		Atencion atencion = new Atencion();
		atencion.setCliente(clienteRepository.findById(idCliente).orElseThrow());
		atencion.setAgente(agenteRepository.findById(idAgente).orElseThrow());
		atencion.setCategoria(categoriaRepository.findById(idCategoria).orElseThrow());
		Campana campana = idCampana == null ? null : campanaRepository.findById(idCampana).orElse(null);

		Atencion registrada = atencionService.registrarAtencion(atencion, mensajes, campana);
		model.addAttribute("atencion", registrada);
		return "atenciones/resultado";
	}
}
