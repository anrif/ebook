package com.acumen.article.repository;

import com.acumen.article.domain.UserShipping;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the UserShipping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserShippingRepository extends JpaRepository<UserShipping, Long>, JpaSpecificationExecutor<UserShipping> {

    @Query("select user_shipping from UserShipping user_shipping where user_shipping.user.login = ?#{principal.username}")
    List<UserShipping> findByUserIsCurrentUser();

}
