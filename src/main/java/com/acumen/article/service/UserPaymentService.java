package com.acumen.article.service;

import com.acumen.article.domain.UserPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UserPayment.
 */
public interface UserPaymentService {

    /**
     * Save a userPayment.
     *
     * @param userPayment the entity to save
     * @return the persisted entity
     */
    UserPayment save(UserPayment userPayment);

    /**
     * Get all the userPayments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserPayment> findAll(Pageable pageable);

    /**
     * Get the "id" userPayment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserPayment findOne(Long id);

    /**
     * Delete the "id" userPayment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the userPayment corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserPayment> search(String query, Pageable pageable);
}
