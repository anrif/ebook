package com.acumen.article.service;

import com.acumen.article.domain.UserShipping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UserShipping.
 */
public interface UserShippingService {

    /**
     * Save a userShipping.
     *
     * @param userShipping the entity to save
     * @return the persisted entity
     */
    UserShipping save(UserShipping userShipping);

    /**
     * Get all the userShippings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserShipping> findAll(Pageable pageable);

    /**
     * Get the "id" userShipping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserShipping findOne(Long id);

    /**
     * Delete the "id" userShipping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the userShipping corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserShipping> search(String query, Pageable pageable);
}
