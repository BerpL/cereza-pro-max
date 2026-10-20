package pe.edu.cibertec.cerezapromax.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.cibertec.cerezapromax.model.Atencion;

public interface AtencionRepository extends JpaRepository<Atencion, Integer> {

	// Consulta 1: atenciones por rango de fechas y estado
	@Query("SELECT a FROM Atencion a WHERE a.fecha BETWEEN :desde AND :hasta "
			+ "AND (:estado = '' OR a.estado = :estado) ORDER BY a.fecha DESC")
	List<Atencion> buscarPorFechasYEstado(@Param("desde") LocalDateTime desde,
			@Param("hasta") LocalDateTime hasta, @Param("estado") String estado);

	List<Atencion> findTop10ByOrderByFechaDesc();

	List<Atencion> findByClienteIdClienteOrderByFechaAsc(Integer idCliente);

	long countByClienteIdCliente(Integer idCliente);

	List<Atencion> findByFechaAfter(LocalDateTime desde);
}
