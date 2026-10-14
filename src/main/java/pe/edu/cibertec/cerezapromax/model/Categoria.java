package pe.edu.cibertec.cerezapromax.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "categoria")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categoria")
	private Integer idCategoria;

	@NotBlank(message = "El nombre es obligatorio")
	@Column(name = "nombre", length = 60, nullable = false)
	private String nombre;

	@Column(name = "descripcion", length = 200)
	private String descripcion;

	@Column(name = "estado", length = 20)
	private String estado = "ACTIVO";

	public Integer getIdCategoria() { return idCategoria; }
	public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }
}
