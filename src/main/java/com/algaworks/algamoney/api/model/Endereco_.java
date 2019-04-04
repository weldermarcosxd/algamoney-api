package com.algaworks.algamoney.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Endereco.class)
public abstract class Endereco_ {

	public static volatile SingularAttribute<Endereco, Integer> number;
	public static volatile SingularAttribute<Endereco, String> city;
	public static volatile SingularAttribute<Endereco, String> street;
	public static volatile SingularAttribute<Endereco, String> neighborhood;
	public static volatile SingularAttribute<Endereco, String> state;
	public static volatile SingularAttribute<Endereco, String> complement;
	public static volatile SingularAttribute<Endereco, Integer> cep;

	public static final String NUMBER = "number";
	public static final String CITY = "city";
	public static final String STREET = "street";
	public static final String NEIGHBORHOOD = "neighborhood";
	public static final String STATE = "state";
	public static final String COMPLEMENT = "complement";
	public static final String CEP = "cep";

}

