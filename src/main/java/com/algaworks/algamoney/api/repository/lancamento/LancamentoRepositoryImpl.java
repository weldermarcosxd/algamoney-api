package com.algaworks.algamoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.algaworks.algamoney.api.filter.LancamentoFilter;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Lancamento_;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Lancamento> findSome(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> createQuery = criteriaBuilder.createQuery(Lancamento.class);
		Root<Lancamento> root = createQuery.from(Lancamento.class);

		Predicate[] restrictions = criarRestricoes(filter, criteriaBuilder, root);

		createQuery.where(restrictions);

		TypedQuery<Lancamento> query = entityManager.createQuery(createQuery);
		addPagination(query, pageable);
		Long totalCount = getResultLenth(filter);

		return new PageImpl<>(query.getResultList(), pageable, totalCount);
	}

	private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder criteriaBuilder,
			Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (!StringUtils.isEmpty(filter.getDescricao())) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Lancamento_.descricao)),
					"%" + filter.getDescricao() + "%"));
		}

		if (filter.getDataVencimentoDe() != null) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.DATA_VENCIMENTO),
					filter.getDataVencimentoDe()));
		}

		if (filter.getDataVencimentoAte() != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.DATA_VENCIMENTO),
					filter.getDataVencimentoAte()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void addPagination(TypedQuery<Lancamento> query, Pageable pageable) {
		int pageNumber = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		int pageFirst = pageNumber * pageSize;

		query.setFirstResult(pageFirst);
		query.setMaxResults(pageSize);
	}

	private Long getResultLenth(LancamentoFilter filter) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = criteriaBuilder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(filter, criteriaBuilder, root);
		criteria.where(predicates);

		criteria.select(criteriaBuilder.count(root));
		return entityManager.createQuery(criteria).getSingleResult();
	}

}
