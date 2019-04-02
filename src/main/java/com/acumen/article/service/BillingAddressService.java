package com.acumen.article.service;

import com.acumen.article.domain.BillingAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BillingAddress.
 */
public interface BillingAddressService {

    /**
     * Save a billingAddress.
     *
     * @param billingAddress the entity to save
     * @return the persisted entity
     */
    BillingAddress save(BillingAddress billingAddress);

    /**
     * Get all the billingAddresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BillingAddress> findAll(Pageable pageable);

    /**
     * Get the "id" billingAddress.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BillingAddress findOne(Long id);

    /**
     * Delete the "id" billingAddress.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the billingAddress corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BillingAddress> search(String query, Pageable pageable);
}
