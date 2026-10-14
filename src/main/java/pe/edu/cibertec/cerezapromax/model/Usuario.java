package pe.edu.cibertec.cerezapromax.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Integer idUsuario;

	@Column(name = "nombre", length = 100, nullable = false)
	private String nombre;

	@Column(name = "correo", length = 100, nullable = false, unique = true)
	private String correo;

	@Column(name = "password", length = 100, nullable = false)
	private String password;

	@Column(name = "estado", length = 20)
	private String estado = "ACTIVO";

	@ManyToOne
	@JoinColumn(name = "id_rol", nullable = false)
	private Rol rol;

	public Integer getIdUsuario() { return idUsuario; }
	public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getCorreo() { return correo; }
	public void setCorreo(String correo) { this.correo = correo; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }
	public Rol getRol() { return rol; }
	public void setRol(Rol rol) { this.rol = rol; }
}
