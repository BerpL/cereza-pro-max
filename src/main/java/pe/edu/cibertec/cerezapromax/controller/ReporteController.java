package pe.edu.cibertec.cerezapromax.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pe.edu.cibertec.cerezapromax.repository.ReporteRepository;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

	private final ReporteRepository reporteRepository;

	public ReporteController(ReporteRepository reporteRepository) {
		this.reporteRepository = reporteRepository;
	}

	// Reporte 1: atenciones por agente en un rango de fechas
	@GetMapping("/atenciones-por-agente")
	public String atencionesPorAgente(HttpSession session,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
			Model model) {
		if (!LoginController.permitido(session, "ADMINISTRADOR", "SUPERVISOR")) {
			return "redirect:/login";
		}
		if (desde == null) desde = LocalDate.now().withDayOfMonth(1);
		if (hasta == null) hasta = LocalDate.now();
		model.addAttribute("filas", reporteRepository.atencionesPorAgente(desde.atStartOfDay(), hasta.atTime(23, 59, 59)));
		model.addAttribute("desde", desde);
		model.addAttribute("hasta", hasta);
		return "reportes/atenciones_por_agente";
	}

	// Reporte 2: envios de mensajes por estado
	@GetMapping("/envios-por-estado")
	public String enviosPorEstado(HttpSession session, Model model) {
		if (!LoginController.permitido(session, "ADMINISTRADOR", "SUPERVISOR")) {
			return "redirect:/login";
		}
		model.addAttribute("filas", reporteRepository.enviosPorEstado());
		return "reportes/envios_por_estado";
	}
}
