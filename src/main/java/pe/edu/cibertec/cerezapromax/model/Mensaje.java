package pe.edu.cibertec.cerezapromax.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mensaje")
public class Mensaje {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_mensaje")
	private Integer idMensaje;

	@ManyToOne
	@JoinColumn(name = "id_atencion", nullable = false)
	private Atencion atencion;

	@ManyToOne
	@JoinColumn(name = "id_campana")
	private Campana campana;

	@Column(name = "contenido", length = 500, nullable = false)
	private String contenido;

	@Column(name = "estado_envio", length = 20)
	private String estadoEnvio = "PENDIENTE";

	@Column(name = "fecha_envio")
	private LocalDateTime fechaEnvio;

	public Integer getIdMensaje() { return idMensaje; }
	public void setIdMensaje(Integer idMensaje) { this.idMensaje = idMensaje; }
	public Atencion getAtencion() { return atencion; }
	public void setAtencion(Atencion atencion) { this.atencion = atencion; }
	public Campana getCampana() { return campana; }
	public void setCampana(Campana campana) { this.campana = campana; }
	public String getContenido() { return contenido; }
	public void setContenido(String contenido) { this.contenido = contenido; }
	public String getEstadoEnvio() { return estadoEnvio; }
	public void setEstadoEnvio(String estadoEnvio) { this.estadoEnvio = estadoEnvio; }
	public LocalDateTime getFechaEnvio() { return fechaEnvio; }
	public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
}
