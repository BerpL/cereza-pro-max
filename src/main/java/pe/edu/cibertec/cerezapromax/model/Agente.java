package pe.edu.cibertec.cerezapromax.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "agente")
public class Agente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_agente")
	private Integer idAgente;

	@NotBlank(message = "El nombre es obligatorio")
	@Column(name = "nombre", length = 100, nullable = false)
	private String nombre;

	@Column(name = "turno", length = 20)
	private String turno;

	@Column(name = "estado", length = 20)
	private String estado = "ACTIVO";

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	public Integer getIdAgente() { return idAgente; }
	public void setIdAgente(Integer idAgente) { this.idAgente = idAgente; }
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getTurno() { return turno; }
	public void setTurno(String turno) { this.turno = turno; }
	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }
	public Usuario getUsuario() { return usuario; }
	public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
