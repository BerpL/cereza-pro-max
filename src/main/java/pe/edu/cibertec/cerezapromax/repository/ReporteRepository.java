package pe.edu.cibertec.cerezapromax.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.cibertec.cerezapromax.model.Atencion;

public interface ReporteRepository extends JpaRepository<Atencion, Integer> {

	// Reporte 1: cantidad de atenciones por agente en un rango de fechas
	@Query("SELECT a.agente.nombre, COUNT(a), SUM(CASE WHEN a.estado = 'ATENDIDA' THEN 1 ELSE 0 END) "
			+ "FROM Atencion a WHERE a.fecha BETWEEN :desde AND :hasta "
			+ "GROUP BY a.agente.nombre ORDER BY COUNT(a) DESC")
	List<Object[]> atencionesPorAgente(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

	// Reporte 2: cantidad de mensajes por estado de envio
	@Query("SELECT m.estadoEnvio, COUNT(m) FROM Mensaje m GROUP BY m.estadoEnvio ORDER BY COUNT(m) DESC")
	List<Object[]> enviosPorEstado();
}
