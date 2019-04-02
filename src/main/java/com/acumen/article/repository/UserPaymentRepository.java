package com.acumen.article.repository;

import com.acumen.article.domain.UserPayment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the UserPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long>, JpaSpecificationExecutor<UserPayment> {

    @Query("select user_payment from UserPayment user_payment where user_payment.user.login = ?#{principal.username}")
    List<UserPayment> findByUserIsCurrentUser();

}
