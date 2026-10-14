package pe.edu.cibertec.cerezapromax.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.cibertec.cerezapromax.model.Agente;

public interface AgenteRepository extends JpaRepository<Agente, Integer> {

	List<Agente> findByEstado(String estado);
}
