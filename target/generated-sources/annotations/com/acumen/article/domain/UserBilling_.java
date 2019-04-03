package com.acumen.article.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserBilling.class)
public abstract class UserBilling_ {

	public static volatile SingularAttribute<UserBilling, String> userBillingZipcode;
	public static volatile SingularAttribute<UserBilling, String> userBillingCity;
	public static volatile SingularAttribute<UserBilling, String> userBillingCountry;
	public static volatile SingularAttribute<UserBilling, String> userBillingName;
	public static volatile SingularAttribute<UserBilling, Long> id;
	public static volatile SingularAttribute<UserBilling, String> userBillingStreet2;
	public static volatile SingularAttribute<UserBilling, String> userBillingStreet1;
	public static volatile SingularAttribute<UserBilling, String> userBillingState;
	public static volatile SingularAttribute<UserBilling, User> user;

}

