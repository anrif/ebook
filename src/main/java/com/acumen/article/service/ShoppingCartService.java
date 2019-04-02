package com.acumen.article.service;

import com.acumen.article.domain.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ShoppingCart.
 */
public interface ShoppingCartService {

    /**
     * Save a shoppingCart.
     *
     * @param shoppingCart the entity to save
     * @return the persisted entity
     */
    ShoppingCart save(ShoppingCart shoppingCart);

    /**
     * Get all the shoppingCarts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShoppingCart> findAll(Pageable pageable);

    /**
     * Get the "id" shoppingCart.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ShoppingCart findOne(Long id);

    /**
     * Delete the "id" shoppingCart.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shoppingCart corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShoppingCart> search(String query, Pageable pageable);
}
