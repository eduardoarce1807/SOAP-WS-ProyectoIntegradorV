package upeu.proyecto5.soap.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "docente")
public class Docente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "persona_id")
	private Integer id;

	@Column(name = "codigo")
	private String codigo;

	@Column(name = "ordinario")
	private String ordinario;

	@Column(name = "categoria_id")
	private Integer categoria_id;

	@Column(name = "ua_id")
	private Integer ua_id;

	// Constructores

	public Docente() {

	}

	public Docente(String codigo, String ordinario, Integer categoria_id, Integer ua_id) {
		this.codigo = codigo;
		this.ordinario = ordinario;
		this.categoria_id = categoria_id;
		this.ua_id = ua_id;
	}

	// Getters y Setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getOrdinario() {
		return ordinario;
	}

	public void setOrdinario(String ordinario) {
		this.ordinario = ordinario;
	}

	public Integer getCategoria_id() {
		return categoria_id;
	}

	public void setCategoria_id(Integer categoria_id) {
		this.categoria_id = categoria_id;
	}

	public Integer getUa_id() {
		return ua_id;
	}

	public void setUa_id(Integer ua_id) {
		this.ua_id = ua_id;
	}

}
