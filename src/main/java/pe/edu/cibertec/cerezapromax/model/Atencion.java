package pe.edu.cibertec.cerezapromax.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "atencion")
public class Atencion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_atencion")
	private Integer idAtencion;

	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "id_agente", nullable = false)
	private Agente agente;

	@ManyToOne
	@JoinColumn(name = "id_categoria", nullable = false)
	private Categoria categoria;

	@Column(name = "fecha", nullable = false)
	private LocalDateTime fecha = LocalDateTime.now();

	@Column(name = "estado", length = 20)
	private String estado = "PENDIENTE";

	@OneToMany(mappedBy = "atencion", cascade = CascadeType.ALL)
	private List<Mensaje> mensajes = new ArrayList<>();

	public Integer getIdAtencion() { return idAtencion; }
	public void setIdAtencion(Integer idAtencion) { this.idAtencion = idAtencion; }
	public Cliente getCliente() { return cliente; }
	public void setCliente(Cliente cliente) { this.cliente = cliente; }
	public Agente getAgente() { return agente; }
	public void setAgente(Agente agente) { this.agente = agente; }
	public Categoria getCategoria() { return categoria; }
	public void setCategoria(Categoria categoria) { this.categoria = categoria; }
	public LocalDateTime getFecha() { return fecha; }
	public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }
	public List<Mensaje> getMensajes() { return mensajes; }
	public void setMensajes(List<Mensaje> mensajes) { this.mensajes = mensajes; }
}
