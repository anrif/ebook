package com.acumen.article.repository;

import com.acumen.article.domain.Ordered;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Ordered entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderedRepository extends JpaRepository<Ordered, Long>, JpaSpecificationExecutor<Ordered> {
    @Query("select distinct ordered from Ordered ordered left join fetch ordered.shippingAddressses left join fetch ordered.billingAddressses left join fetch ordered.payments")
    List<Ordered> findAllWithEagerRelationships();

    @Query("select ordered from Ordered ordered left join fetch ordered.shippingAddressses left join fetch ordered.billingAddressses left join fetch ordered.payments where ordered.id =:id")
    Ordered findOneWithEagerRelationships(@Param("id") Long id);

}
