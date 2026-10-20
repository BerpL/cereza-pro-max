package pe.edu.cibertec.cerezapromax.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.cibertec.cerezapromax.model.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {

	// Consulta 2: mensajes por cliente o por campana
	@Query("SELECT m FROM Mensaje m WHERE (:idCliente IS NULL OR m.atencion.cliente.idCliente = :idCliente) "
			+ "AND (:idCampana IS NULL OR m.campana.idCampana = :idCampana) ORDER BY m.fechaEnvio DESC")
	List<Mensaje> buscarPorClienteOCampana(@Param("idCliente") Integer idCliente,
			@Param("idCampana") Integer idCampana);

	Mensaje findFirstByAtencionClienteIdClienteOrderByIdMensajeDesc(Integer idCliente);

	long countByAtencionClienteIdCliente(Integer idCliente);

	// evita el envio duplicado: mismo contenido para el mismo cliente
	long countByAtencionClienteIdClienteAndContenidoAndEstadoEnvio(Integer idCliente, String contenido, String estadoEnvio);
}
