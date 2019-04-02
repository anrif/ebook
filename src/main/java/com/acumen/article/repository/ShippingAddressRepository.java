package com.acumen.article.repository;

import com.acumen.article.domain.ShippingAddress;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ShippingAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long>, JpaSpecificationExecutor<ShippingAddress> {

}
