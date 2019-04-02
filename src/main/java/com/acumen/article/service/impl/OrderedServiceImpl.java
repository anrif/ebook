package com.acumen.article.service.impl;

import com.acumen.article.service.OrderedService;
import com.acumen.article.domain.Ordered;
import com.acumen.article.repository.OrderedRepository;
import com.acumen.article.repository.search.OrderedSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Ordered.
 */
@Service
@Transactional
public class OrderedServiceImpl implements OrderedService {

    private final Logger log = LoggerFactory.getLogger(OrderedServiceImpl.class);

    private final OrderedRepository orderedRepository;

    private final OrderedSearchRepository orderedSearchRepository;

    public OrderedServiceImpl(OrderedRepository orderedRepository, OrderedSearchRepository orderedSearchRepository) {
        this.orderedRepository = orderedRepository;
        this.orderedSearchRepository = orderedSearchRepository;
    }

    /**
     * Save a ordered.
     *
     * @param ordered the entity to save
     * @return the persisted entity
     */
    @Override
    public Ordered save(Ordered ordered) {
        log.debug("Request to save Ordered : {}", ordered);
        Ordered result = orderedRepository.save(ordered);
        orderedSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the ordereds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Ordered> findAll(Pageable pageable) {
        log.debug("Request to get all Ordereds");
        return orderedRepository.findAll(pageable);
    }

    /**
     * Get one ordered by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Ordered findOne(Long id) {
        log.debug("Request to get Ordered : {}", id);
        return orderedRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the ordered by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ordered : {}", id);
        orderedRepository.delete(id);
        orderedSearchRepository.delete(id);
    }

    /**
     * Search for the ordered corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Ordered> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ordereds for query {}", query);
        Page<Ordered> result = orderedSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
