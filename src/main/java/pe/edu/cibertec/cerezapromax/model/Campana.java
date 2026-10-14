package pe.edu.cibertec.cerezapromax.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "campana")
public class Campana {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_campana")
	private Integer idCampana;

	@Column(name = "nombre", length = 100, nullable = false)
	private String nombre;

	@Column(name = "estado", length = 20)
	private String estado = "ACTIVA";

	public Integer getIdCampana() { return idCampana; }
	public void setIdCampana(Integer idCampana) { this.idCampana = idCampana; }
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }
}
