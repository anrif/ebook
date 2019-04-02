package com.acumen.article.repository;

import com.acumen.article.domain.ShoppingCart;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the ShoppingCart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>, JpaSpecificationExecutor<ShoppingCart> {

    @Query("select shopping_cart from ShoppingCart shopping_cart where shopping_cart.user.login = ?#{principal.username}")
    List<ShoppingCart> findByUserIsCurrentUser();

}
