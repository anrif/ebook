package com.acumen.article.domain;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CartItem.class)
public abstract class CartItem_ {

	public static volatile SingularAttribute<CartItem, Ordered> ordered;
	public static volatile SingularAttribute<CartItem, BigDecimal> subtotal;
	public static volatile SingularAttribute<CartItem, Integer> qty;
	public static volatile SingularAttribute<CartItem, Book> book;
	public static volatile SingularAttribute<CartItem, ShoppingCart> shoppingCart;
	public static volatile SingularAttribute<CartItem, Long> id;

}

