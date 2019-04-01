package com.algaworks.algamoney.api.resources;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.events.ResourceCreatedEvent;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Pessoa> findAll(){
		return pessoaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> add(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa entity = pessoaRepository.save(pessoa);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, pessoa.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(entity);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> findOne(@PathVariable Long id) {
		Optional<Pessoa> pessoaOpt = pessoaRepository.findById(id);
		if(!pessoaOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}else {
			Pessoa pessoa = pessoaOpt.get();
			return ResponseEntity.ok(pessoa);
		}
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Pessoa updateOne(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaSalva = pessoaService.updateOne(id, pessoa);
		return pessoaSalva;
	}
	
	@PutMapping("/{id}/active")
	@ResponseStatus(HttpStatus.OK)
	public Pessoa updateStatus(@PathVariable Long id, @Valid @RequestBody Boolean active){
		Pessoa pessoaSalva = pessoaService.updateStatus(id, active);
		return pessoaSalva;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeOne(@PathVariable Long id) {
		pessoaRepository.deleteById(id);
	}
}
