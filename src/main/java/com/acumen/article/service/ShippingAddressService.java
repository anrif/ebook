package com.acumen.article.service;

import com.acumen.article.domain.ShippingAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ShippingAddress.
 */
public interface ShippingAddressService {

    /**
     * Save a shippingAddress.
     *
     * @param shippingAddress the entity to save
     * @return the persisted entity
     */
    ShippingAddress save(ShippingAddress shippingAddress);

    /**
     * Get all the shippingAddresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShippingAddress> findAll(Pageable pageable);

    /**
     * Get the "id" shippingAddress.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ShippingAddress findOne(Long id);

    /**
     * Delete the "id" shippingAddress.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shippingAddress corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShippingAddress> search(String query, Pageable pageable);
}
