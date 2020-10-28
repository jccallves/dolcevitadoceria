package com.dolcevitadoceria.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dolcevitadoceria.domain.Estado;
import com.dolcevitadoceria.repositories.EstadoRepository;


@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;

	public List<Estado> findAll() {
		return repo.findAllByOrderByNome();
	}
}