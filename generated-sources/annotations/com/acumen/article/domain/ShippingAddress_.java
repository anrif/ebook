package com.acumen.article.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ShippingAddress.class)
public abstract class ShippingAddress_ {

	public static volatile SingularAttribute<ShippingAddress, String> shippingAddressCity;
	public static volatile SingularAttribute<ShippingAddress, String> shippingAddressState;
	public static volatile SingularAttribute<ShippingAddress, String> shippingAddressStreet2;
	public static volatile SetAttribute<ShippingAddress, Ordered> ordereds;
	public static volatile SingularAttribute<ShippingAddress, String> shippingAddressStreet1;
	public static volatile SingularAttribute<ShippingAddress, String> shippingAddressName;
	public static volatile SingularAttribute<ShippingAddress, Long> id;
	public static volatile SingularAttribute<ShippingAddress, String> shippingAddressZipcode;
	public static volatile SingularAttribute<ShippingAddress, String> shippingAddressCountry;

}

