package pe.edu.cibertec.cerezapromax.controller;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import pe.edu.cibertec.cerezapromax.model.Atencion;
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
		model.addAttribute("duplicados", mensajeRepository.countByEstadoEnvio("DUPLICADO"));
		model.addAttribute("ultimas", atencionRepository.findTop10ByOrderByFechaDesc());
		model.addAttribute("semana", ultimosSieteDias());
		return "panel";
	}

	/** Dia de la semana, atenciones y altura relativa (0-100) para la grafica del panel. */
	public record Dia(String etiqueta, long total, int alto, boolean hoy) {}

	private List<Dia> ultimosSieteDias() {
		LocalDate hoy = LocalDate.now();
		List<Atencion> recientes = atencionRepository.findByFechaAfter(hoy.minusDays(6).atStartOfDay());
		long[] conteo = new long[7];
		for (Atencion a : recientes) {
			int idx = (int) (a.getFecha().toLocalDate().toEpochDay() - hoy.minusDays(6).toEpochDay());
			if (idx >= 0 && idx < 7) conteo[idx]++;
		}
		long max = 1;
		for (long c : conteo) max = Math.max(max, c);
		List<Dia> dias = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			LocalDate d = hoy.minusDays(6 - i);
			String et = d.getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("es", "PE")).replace(".", "");
			dias.add(new Dia(et.substring(0, 1).toUpperCase() + et.substring(1), conteo[i], (int) (conteo[i] * 100 / max), i == 6));
		}
		return dias;
	}
}
