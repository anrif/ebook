package com.acumen.article.repository;

import com.acumen.article.domain.UserBilling;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the UserBilling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserBillingRepository extends JpaRepository<UserBilling, Long>, JpaSpecificationExecutor<UserBilling> {

    @Query("select user_billing from UserBilling user_billing where user_billing.user.login = ?#{principal.username}")
    List<UserBilling> findByUserIsCurrentUser();

}
