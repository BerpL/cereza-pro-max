package pe.edu.cibertec.cerezapromax.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pe.edu.cibertec.cerezapromax.repository.AtencionRepository;
import pe.edu.cibertec.cerezapromax.repository.CampanaRepository;
import pe.edu.cibertec.cerezapromax.repository.ClienteRepository;
import pe.edu.cibertec.cerezapromax.repository.MensajeRepository;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

	private final AtencionRepository atencionRepository;
	private final MensajeRepository mensajeRepository;
	private final ClienteRepository clienteRepository;
	private final CampanaRepository campanaRepository;

	public ConsultaController(AtencionRepository atencionRepository, MensajeRepository mensajeRepository,
			ClienteRepository clienteRepository, CampanaRepository campanaRepository) {
		this.atencionRepository = atencionRepository;
		this.mensajeRepository = mensajeRepository;
		this.clienteRepository = clienteRepository;
		this.campanaRepository = campanaRepository;
	}

	// Consulta 1: atenciones por rango de fechas y estado
	@GetMapping("/atenciones")
	public String atenciones(HttpSession session,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
			@RequestParam(required = false, defaultValue = "") String estado, Model model) {
		if (!LoginController.permitido(session, "ADMINISTRADOR", "SUPERVISOR")) {
			return "redirect:/login";
		}
		if (desde != null && hasta != null) {
			model.addAttribute("atenciones", atencionRepository.buscarPorFechasYEstado(
					desde.atStartOfDay(), hasta.atTime(23, 59, 59), estado));
		}
		model.addAttribute("desde", desde);
		model.addAttribute("hasta", hasta);
		model.addAttribute("estado", estado);
		return "consultas/atenciones";
	}

	// Consulta 2: mensajes por cliente o por campana
	@GetMapping("/mensajes")
	public String mensajes(HttpSession session, @RequestParam(required = false) Integer idCliente,
			@RequestParam(required = false) Integer idCampana, Model model) {
		if (!LoginController.permitido(session, "ADMINISTRADOR", "SUPERVISOR")) {
			return "redirect:/login";
		}
		if (idCliente != null || idCampana != null) {
			model.addAttribute("mensajes", mensajeRepository.buscarPorClienteOCampana(idCliente, idCampana));
		}
		model.addAttribute("clientes", clienteRepository.findAll());
		model.addAttribute("campanas", campanaRepository.findAll());
		model.addAttribute("idCliente", idCliente);
		model.addAttribute("idCampana", idCampana);
		return "consultas/mensajes";
	}
}
