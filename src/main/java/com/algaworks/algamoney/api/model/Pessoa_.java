package com.algaworks.algamoney.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pessoa.class)
public abstract class Pessoa_ {

	public static volatile SingularAttribute<Pessoa, Endereco> endereco;
	public static volatile SingularAttribute<Pessoa, String> name;
	public static volatile SingularAttribute<Pessoa, Boolean> active;
	public static volatile SingularAttribute<Pessoa, Long> id;

	public static final String ENDERECO = "endereco";
	public static final String NAME = "name";
	public static final String ACTIVE = "active";
	public static final String ID = "id";

}

