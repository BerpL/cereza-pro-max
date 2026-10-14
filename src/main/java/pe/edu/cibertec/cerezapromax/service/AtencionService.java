package pe.edu.cibertec.cerezapromax.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.cibertec.cerezapromax.model.Atencion;
import pe.edu.cibertec.cerezapromax.model.Campana;
import pe.edu.cibertec.cerezapromax.model.Mensaje;
import pe.edu.cibertec.cerezapromax.repository.AtencionRepository;
import pe.edu.cibertec.cerezapromax.repository.MensajeRepository;

@Service
public class AtencionService {

	private final AtencionRepository atencionRepository;
	private final MensajeRepository mensajeRepository;

	public AtencionService(AtencionRepository atencionRepository, MensajeRepository mensajeRepository) {
		this.atencionRepository = atencionRepository;
		this.mensajeRepository = mensajeRepository;
	}

	/**
	 * Caso transaccional: registra la atencion y despacha sus mensajes en una sola
	 * unidad. Si un mensaje ya fue enviado al mismo cliente no se vuelve a enviar y
	 * si algo falla se revierte todo (rollback).
	 */
	@Transactional
	public Atencion registrarAtencion(Atencion atencion, List<String> contenidos, Campana campana) {
		atencion.setEstado("EN_CURSO");
		Atencion guardada = atencionRepository.save(atencion);

		for (String contenido : contenidos) {
			if (contenido == null || contenido.isBlank()) {
				continue;
			}
			long yaEnviados = mensajeRepository.countByAtencionClienteIdClienteAndContenidoAndEstadoEnvio(
					guardada.getCliente().getIdCliente(), contenido, "ENVIADO");

			Mensaje m = new Mensaje();
			m.setAtencion(guardada);
			m.setCampana(campana);
			m.setContenido(contenido);
			m.setFechaEnvio(LocalDateTime.now());
			m.setEstadoEnvio(yaEnviados > 0 ? "DUPLICADO" : "ENVIADO");
			mensajeRepository.save(m);
			guardada.getMensajes().add(m);
		}

		guardada.setEstado("ATENDIDA");
		return atencionRepository.save(guardada);
	}
}
