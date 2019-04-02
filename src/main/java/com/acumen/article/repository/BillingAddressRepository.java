package com.acumen.article.repository;

import com.acumen.article.domain.BillingAddress;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BillingAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long>, JpaSpecificationExecutor<BillingAddress> {

}
