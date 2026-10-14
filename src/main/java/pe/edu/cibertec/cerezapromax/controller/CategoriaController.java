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
import pe.edu.cibertec.cerezapromax.model.Categoria;
import pe.edu.cibertec.cerezapromax.repository.CategoriaRepository;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

	private final CategoriaRepository categoriaRepository;

	public CategoriaController(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	@GetMapping
	public String listar(HttpSession session, Model model) {
		if (!LoginController.permitido(session, "ADMINISTRADOR")) {
			return "redirect:/login";
		}
		model.addAttribute("categorias", categoriaRepository.findAll());
		return "categorias/lista";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("categoria", new Categoria());
		return "categorias/form";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Integer id, Model model) {
		model.addAttribute("categoria", categoriaRepository.findById(id).orElse(new Categoria()));
		return "categorias/form";
	}

	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute Categoria categoria, BindingResult result) {
		if (result.hasErrors()) {
			return "categorias/form";
		}
		categoriaRepository.save(categoria);
		return "redirect:/categorias";
	}

	@GetMapping("/estado/{id}")
	public String cambiarEstado(@PathVariable Integer id) {
		categoriaRepository.findById(id).ifPresent(c -> {
			c.setEstado("ACTIVO".equals(c.getEstado()) ? "INACTIVO" : "ACTIVO");
			categoriaRepository.save(c);
		});
		return "redirect:/categorias";
	}
}
