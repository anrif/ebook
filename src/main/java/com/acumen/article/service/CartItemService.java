package com.acumen.article.service;

import com.acumen.article.domain.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CartItem.
 */
public interface CartItemService {

    /**
     * Save a cartItem.
     *
     * @param cartItem the entity to save
     * @return the persisted entity
     */
    CartItem save(CartItem cartItem);

    /**
     * Get all the cartItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CartItem> findAll(Pageable pageable);

    /**
     * Get the "id" cartItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CartItem findOne(Long id);

    /**
     * Delete the "id" cartItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cartItem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CartItem> search(String query, Pageable pageable);
}
