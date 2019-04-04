package com.algaworks.algamoney.api.resources;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoney.api.events.ResourceCreatedEvent;
import com.algaworks.algamoney.api.exceptionhandler.AlgaMoneyExceptionHandler.Error;
import com.algaworks.algamoney.api.filter.LancamentoFilter;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.services.LancamentoService;
import com.algaworks.algamoney.api.services.exceptions.PessoaInactiveException;
import com.algaworks.algamoney.api.services.exceptions.PessoaNotFoundException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired 
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public Page<Lancamento> findAll(Pageable pageable){
		return lancamentoRepository.findAll(pageable);
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> add(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoService.save(lancamento);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, lancamento.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Lancamento> findOne(@PathVariable Long id) {
		Optional<Lancamento> lancamentoOpt = lancamentoRepository.findById(id);
		if(!lancamentoOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}else {
			Lancamento lancamento = lancamentoOpt.get();
			return ResponseEntity.ok(lancamento);
		}
	}
	
	@GetMapping("/search")
	public Page<Lancamento> findSome(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.findSome(lancamentoFilter, pageable);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Lancamento updateOne(@PathVariable Long id, @Valid @RequestBody Lancamento lancamento){
		Lancamento lancamentoSalvo = lancamentoService.updateOne(id, lancamento);
		return lancamentoSalvo;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeOne(@PathVariable Long id) {
		lancamentoRepository.deleteById(id);
	}
	
	@ExceptionHandler({PessoaInactiveException.class})
	public ResponseEntity<List<Error>> handlePessoaInactiveException(PessoaInactiveException ex) {
		String userMessage = messageSource.getMessage("resource.person_inactive", null, LocaleContextHolder.getLocale());
		String stacktrace = ex.toString();
		List<Error> errorList = Arrays.asList(new Error(userMessage, stacktrace));
		return ResponseEntity.badRequest().body(errorList);
	}
	
	@ExceptionHandler({PessoaNotFoundException.class})
	public ResponseEntity<List<Error>> handlePessoaNotFoundException(PessoaNotFoundException ex) {
		String userMessage = messageSource.getMessage("resource.detail_not_found", null, LocaleContextHolder.getLocale());
		String stacktrace = ex.toString();
		List<Error> errorList = Arrays.asList(new Error(userMessage, stacktrace));
		return ResponseEntity.badRequest().body(errorList);
	}
}
