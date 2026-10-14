package pe.edu.cibertec.cerezapromax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pe.edu.cibertec.cerezapromax.model.Usuario;
import pe.edu.cibertec.cerezapromax.service.UsuarioService;

@Controller
public class LoginController {

	private final UsuarioService usuarioService;

	public LoginController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public String validar(@RequestParam String correo, @RequestParam String password,
			HttpSession session, Model model) {
		return usuarioService.validar(correo, password).map(u -> {
			// la sesion guarda al usuario y su rol
			session.setAttribute("usuario", u);
			session.setAttribute("rol", u.getRol().getNombre());
			return "redirect:/";
		}).orElseGet(() -> {
			model.addAttribute("error", "Correo o contraseña incorrectos");
			return "login";
		});
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	/** Comprueba que haya sesion y que el rol este permitido. */
	public static boolean permitido(HttpSession session, String... roles) {
		Usuario u = (Usuario) session.getAttribute("usuario");
		if (u == null) {
			return false;
		}
		String rol = u.getRol().getNombre();
		for (String r : roles) {
			if (r.equals(rol)) {
				return true;
			}
		}
		return false;
	}
}
