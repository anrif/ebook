package com.acumen.article.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Ordered.class)
public abstract class Ordered_ {

	public static volatile SingularAttribute<Ordered, LocalDate> shippingDate;
	public static volatile SetAttribute<Ordered, ShippingAddress> shippingAddressses;
	public static volatile SingularAttribute<Ordered, String> shippingMethod;
	public static volatile SetAttribute<Ordered, Payment> payments;
	public static volatile SingularAttribute<Ordered, String> orderStatus;
	public static volatile SingularAttribute<Ordered, Long> id;
	public static volatile SetAttribute<Ordered, BillingAddress> billingAddressses;
	public static volatile SingularAttribute<Ordered, LocalDate> orderDate;
	public static volatile SingularAttribute<Ordered, BigDecimal> orderTotal;

}

