package pe.edu.cibertec.cerezapromax.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.cibertec.cerezapromax.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

	List<Categoria> findByEstado(String estado);
}
