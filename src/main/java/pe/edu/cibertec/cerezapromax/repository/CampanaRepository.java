package pe.edu.cibertec.cerezapromax.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.cibertec.cerezapromax.model.Campana;

public interface CampanaRepository extends JpaRepository<Campana, Integer> {

	List<Campana> findByEstado(String estado);
}
