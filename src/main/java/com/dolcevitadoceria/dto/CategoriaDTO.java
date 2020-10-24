package com.dolcevitadoceria.dto;
import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.dolcevitadoceria.domain.Categoria;

public class CategoriaDTO implements Serializable {

	
	private static final long serialVersionUID = 2259052414882391487L;
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")

	@Length(min = 5, max = 80, message 
    = "About Me must be between 10 and 200 characters")
	private String nome;

	public CategoriaDTO() {
	}

	public CategoriaDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
}
