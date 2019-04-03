package com.acumen.article.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BillingAddress.class)
public abstract class BillingAddress_ {

	public static volatile SingularAttribute<BillingAddress, String> billingAddressState;
	public static volatile SingularAttribute<BillingAddress, String> billingAddressCity;
	public static volatile SetAttribute<BillingAddress, Ordered> ordereds;
	public static volatile SingularAttribute<BillingAddress, String> billingAddressStreet2;
	public static volatile SingularAttribute<BillingAddress, String> billingAddressStreet1;
	public static volatile SingularAttribute<BillingAddress, String> billingAddressName;
	public static volatile SingularAttribute<BillingAddress, String> billingAddressZipcode;
	public static volatile SingularAttribute<BillingAddress, Long> id;
	public static volatile SingularAttribute<BillingAddress, String> billingAddressCountry;

}

