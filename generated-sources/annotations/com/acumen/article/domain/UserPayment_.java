package com.acumen.article.domain;

import com.acumen.article.domain.enumeration.TypePayment;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserPayment.class)
public abstract class UserPayment_ {

	public static volatile SingularAttribute<UserPayment, Integer> cvc;
	public static volatile SingularAttribute<UserPayment, String> holderName;
	public static volatile SingularAttribute<UserPayment, String> cardName;
	public static volatile SingularAttribute<UserPayment, Boolean> defaultPayment;
	public static volatile SingularAttribute<UserPayment, Integer> expiryMonth;
	public static volatile SingularAttribute<UserPayment, Long> id;
	public static volatile SingularAttribute<UserPayment, Integer> expiryYear;
	public static volatile SingularAttribute<UserPayment, TypePayment> type;
	public static volatile SingularAttribute<UserPayment, User> user;
	public static volatile SingularAttribute<UserPayment, String> cardNumber;

}

