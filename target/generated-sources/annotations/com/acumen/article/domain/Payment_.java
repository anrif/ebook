package com.acumen.article.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Payment.class)
public abstract class Payment_ {

	public static volatile SingularAttribute<Payment, Integer> cvc;
	public static volatile SingularAttribute<Payment, String> holderName;
	public static volatile SetAttribute<Payment, Ordered> ordereds;
	public static volatile SingularAttribute<Payment, Integer> expiryMonth;
	public static volatile SingularAttribute<Payment, Long> id;
	public static volatile SingularAttribute<Payment, String> cartName;
	public static volatile SingularAttribute<Payment, Integer> expiryYear;
	public static volatile SingularAttribute<Payment, String> type;
	public static volatile SingularAttribute<Payment, String> cardNumber;

}

