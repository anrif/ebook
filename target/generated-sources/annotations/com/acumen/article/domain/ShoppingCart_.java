package com.acumen.article.domain;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ShoppingCart.class)
public abstract class ShoppingCart_ {

	public static volatile SingularAttribute<ShoppingCart, BigDecimal> grandTotal;
	public static volatile SingularAttribute<ShoppingCart, Long> id;
	public static volatile SingularAttribute<ShoppingCart, User> user;

}

