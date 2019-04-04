package com.algaworks.algamoney.api.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.services.exceptions.PessoaInactiveException;
import com.algaworks.algamoney.api.services.exceptions.PessoaNotFoundException;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired 
	private PessoaRepository pessoaRepository;
	
	public Lancamento updateOne(Long id, @Valid Lancamento lancamento) {
		Lancamento lancamentoSalvo = findLancamento(id);
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "id");
		return lancamentoRepository.save(lancamentoSalvo);
	}

	private Lancamento findLancamento(Long id) {
		Optional<Lancamento> lancamentoSalvo = lancamentoRepository.findById(id);
		if(!lancamentoSalvo.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamentoSalvo.get();
	}

	public Lancamento save(@Valid Lancamento lancamento) {
		Optional<Pessoa> pessoaOpt = pessoaRepository.findById(lancamento.getPessoa().getId());
		
		if(!pessoaOpt.isPresent()) {
			throw new PessoaNotFoundException();
		}
		
		Pessoa pessoa = pessoaOpt.get();
		
		if(pessoa.isInactive()){
			throw new PessoaInactiveException();
		}
		
		return lancamentoRepository.save(lancamento);
	}
}
