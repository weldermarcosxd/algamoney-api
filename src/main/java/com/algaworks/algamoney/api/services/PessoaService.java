package com.algaworks.algamoney.api.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa updateOne(Long id, @Valid Pessoa pessoa) {
		Pessoa pessoaSalva = findPessoa(id);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
		return pessoaRepository.save(pessoaSalva);
	}

	public Pessoa updateStatus(Long id, @Valid Boolean active) {
		Pessoa pessoaSalva = findPessoa(id);
		pessoaSalva.setActive(active);
		return pessoaRepository.save(pessoaSalva);
	}
	
	private Pessoa findPessoa(Long id) {
		Optional<Pessoa> pessoaSalva = pessoaRepository.findById(id);
		if(!pessoaSalva.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva.get();
	}

}
