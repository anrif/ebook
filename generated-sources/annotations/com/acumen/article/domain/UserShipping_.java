package com.acumen.article.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserShipping.class)
public abstract class UserShipping_ {

	public static volatile SingularAttribute<UserShipping, Boolean> userShippingDefault;
	public static volatile SingularAttribute<UserShipping, String> userShippingName;
	public static volatile SingularAttribute<UserShipping, String> userShippingStreet2;
	public static volatile SingularAttribute<UserShipping, String> userShippingStreet1;
	public static volatile SingularAttribute<UserShipping, Long> id;
	public static volatile SingularAttribute<UserShipping, String> userShippingCity;
	public static volatile SingularAttribute<UserShipping, String> userShippingZipcode;
	public static volatile SingularAttribute<UserShipping, User> user;
	public static volatile SingularAttribute<UserShipping, String> userShippingState;
	public static volatile SingularAttribute<UserShipping, String> userShippingCountry;

}

