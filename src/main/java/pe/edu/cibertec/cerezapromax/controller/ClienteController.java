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
import pe.edu.cibertec.cerezapromax.model.Cliente;
import pe.edu.cibertec.cerezapromax.repository.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	private final ClienteRepository clienteRepository;

	public ClienteController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@GetMapping
	public String listar(HttpSession session, Model model) {
		if (!LoginController.permitido(session, "ADMINISTRADOR")) {
			return "redirect:/login";
		}
		model.addAttribute("clientes", clienteRepository.findAll());
		return "clientes/lista";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "clientes/form";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Integer id, Model model) {
		model.addAttribute("cliente", clienteRepository.findById(id).orElse(new Cliente()));
		return "clientes/form";
	}

	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute Cliente cliente, BindingResult result) {
		if (result.hasErrors()) {
			return "clientes/form";
		}
		clienteRepository.save(cliente);
		return "redirect:/clientes";
	}

	@GetMapping("/estado/{id}")
	public String cambiarEstado(@PathVariable Integer id) {
		clienteRepository.findById(id).ifPresent(c -> {
			c.setEstado("ACTIVO".equals(c.getEstado()) ? "INACTIVO" : "ACTIVO");
			clienteRepository.save(c);
		});
		return "redirect:/clientes";
	}
}
