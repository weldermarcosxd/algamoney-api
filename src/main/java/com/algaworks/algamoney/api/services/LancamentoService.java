package com.algaworks.algamoney.api.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.LancamentoRepository;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

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

}
