package com.acumen.article.service;

import com.acumen.article.domain.Ordered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Ordered.
 */
public interface OrderedService {

    /**
     * Save a ordered.
     *
     * @param ordered the entity to save
     * @return the persisted entity
     */
    Ordered save(Ordered ordered);

    /**
     * Get all the ordereds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Ordered> findAll(Pageable pageable);

    /**
     * Get the "id" ordered.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Ordered findOne(Long id);

    /**
     * Delete the "id" ordered.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the ordered corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Ordered> search(String query, Pageable pageable);
}
