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
@Table(name = "unidad_academica")
public class UnidadAcademica implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ua_id")
	private Integer id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "padre_id")
	private Integer padre_id;

	@Column(name = "tipo_ua_id")
	private Integer tipo_ua_id;

	// Constructores

	public UnidadAcademica() {

	}

	public UnidadAcademica(String nombre, Integer padre_id, Integer tipo_ua_id) {
		this.nombre = nombre;
		this.padre_id = padre_id;
		this.tipo_ua_id = tipo_ua_id;
	}

	// Getters y Setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPadre_id() {
		return padre_id;
	}

	public void setPadre_id(Integer padre_id) {
		this.padre_id = padre_id;
	}

	public Integer getTipo_ua_id() {
		return tipo_ua_id;
	}

	public void setTipo_ua_id(Integer tipo_ua_id) {
		this.tipo_ua_id = tipo_ua_id;
	}

}
