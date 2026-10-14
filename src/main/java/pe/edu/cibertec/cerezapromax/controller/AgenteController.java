package pe.edu.cibertec.cerezapromax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import pe.edu.cibertec.cerezapromax.model.Agente;
import pe.edu.cibertec.cerezapromax.repository.AgenteRepository;

@Controller
@RequestMapping("/agentes")
public class AgenteController {

	private final AgenteRepository agenteRepository;

	public AgenteController(AgenteRepository agenteRepository) {
		this.agenteRepository = agenteRepository;
	}

	@GetMapping
	public String listar(HttpSession session, Model model) {
		if (!LoginController.permitido(session, "ADMINISTRADOR")) {
			return "redirect:/login";
		}
		model.addAttribute("agentes", agenteRepository.findAll());
		return "agentes/lista";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("agente", new Agente());
		return "agentes/form";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Integer id, Model model) {
		model.addAttribute("agente", agenteRepository.findById(id).orElse(new Agente()));
		return "agentes/form";
	}

	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute Agente agente, BindingResult result) {
		if (result.hasErrors()) {
			return "agentes/form";
		}
		agenteRepository.save(agente);
		return "redirect:/agentes";
	}

	@GetMapping("/estado/{id}")
	public String cambiarEstado(@PathVariable Integer id) {
		agenteRepository.findById(id).ifPresent(a -> {
			a.setEstado("ACTIVO".equals(a.getEstado()) ? "INACTIVO" : "ACTIVO");
			agenteRepository.save(a);
		});
		return "redirect:/agentes";
	}
}
