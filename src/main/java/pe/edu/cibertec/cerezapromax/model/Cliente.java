package pe.edu.cibertec.cerezapromax.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	private Integer idCliente;

	@NotBlank(message = "El nombre es obligatorio")
	@Column(name = "nombre", length = 100, nullable = false)
	private String nombre;

	@NotBlank(message = "El telefono es obligatorio")
	@Column(name = "telefono", length = 15, nullable = false)
	private String telefono;

	@Column(name = "correo", length = 100)
	private String correo;

	@Column(name = "estado", length = 20)
	private String estado = "ACTIVO";

	@Column(name = "fecha_registro")
	private LocalDateTime fechaRegistro = LocalDateTime.now();

	public Integer getIdCliente() { return idCliente; }
	public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getTelefono() { return telefono; }
	public void setTelefono(String telefono) { this.telefono = telefono; }
	public String getCorreo() { return correo; }
	public void setCorreo(String correo) { this.correo = correo; }
	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }
	public LocalDateTime getFechaRegistro() { return fechaRegistro; }
	public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
