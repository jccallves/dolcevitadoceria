package com.dolcevitadoceria.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.dolcevitadoceria.domain.Categoria;
import com.dolcevitadoceria.repositories.CategoriaRepository;
import com.dolcevitadoceria.services.exceptions.DataIntegrityException;
import com.dolcevitadoceria.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria getCategoriaById (Integer id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return categoriaRepository.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		getCategoriaById(obj.getId());
		return categoriaRepository.save(obj);
	}
	
	public void delete(Integer id) {
		getCategoriaById(id);
		try {
			categoriaRepository.deleteById(id);;
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
}
