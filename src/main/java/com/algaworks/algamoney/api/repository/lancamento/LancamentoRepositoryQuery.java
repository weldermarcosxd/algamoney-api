package com.algaworks.algamoney.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoney.api.filter.LancamentoFilter;
import com.algaworks.algamoney.api.model.Lancamento;

public interface LancamentoRepositoryQuery {
	
	public Page<Lancamento> findSome(LancamentoFilter filter, Pageable pageable);
	
}
