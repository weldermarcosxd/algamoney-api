package com.algaworks.algamoney.api.resources;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.events.ResourceCreatedEvent;
import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Categoria> findAll(){
		return categoriaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> add(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria entity = categoriaRepository.save(categoria);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, categoria.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(entity);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> findOne(@PathVariable Long id) {
		Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
		if(!categoriaOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}else {
			Categoria categoria = categoriaOpt.get();
			return ResponseEntity.ok(categoria);
		}
	}
}
