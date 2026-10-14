package pe.edu.cibertec.cerezapromax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import pe.edu.cibertec.cerezapromax.repository.AgenteRepository;
import pe.edu.cibertec.cerezapromax.repository.AtencionRepository;
import pe.edu.cibertec.cerezapromax.repository.ClienteRepository;
import pe.edu.cibertec.cerezapromax.repository.MensajeRepository;

@Controller
public class HomeController {

	private final ClienteRepository clienteRepository;
	private final AgenteRepository agenteRepository;
	private final AtencionRepository atencionRepository;
	private final MensajeRepository mensajeRepository;

	public HomeController(ClienteRepository clienteRepository, AgenteRepository agenteRepository,
			AtencionRepository atencionRepository, MensajeRepository mensajeRepository) {
		this.clienteRepository = clienteRepository;
		this.agenteRepository = agenteRepository;
		this.atencionRepository = atencionRepository;
		this.mensajeRepository = mensajeRepository;
	}

	@GetMapping("/")
	public String panel(HttpSession session, Model model) {
		if (!LoginController.permitido(session, "ADMINISTRADOR", "SUPERVISOR", "AGENTE")) {
			return "redirect:/login";
		}
		model.addAttribute("totalClientes", clienteRepository.count());
		model.addAttribute("totalAgentes", agenteRepository.count());
		model.addAttribute("totalAtenciones", atencionRepository.count());
		model.addAttribute("totalMensajes", mensajeRepository.count());
		model.addAttribute("ultimas", atencionRepository.findTop10ByOrderByFechaDesc());
		return "panel";
	}
}
