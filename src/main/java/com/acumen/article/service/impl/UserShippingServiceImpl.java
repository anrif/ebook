package com.acumen.article.service.impl;

import com.acumen.article.service.UserShippingService;
import com.acumen.article.domain.UserShipping;
import com.acumen.article.repository.UserShippingRepository;
import com.acumen.article.repository.search.UserShippingSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UserShipping.
 */
@Service
@Transactional
public class UserShippingServiceImpl implements UserShippingService {

    private final Logger log = LoggerFactory.getLogger(UserShippingServiceImpl.class);

    private final UserShippingRepository userShippingRepository;

    private final UserShippingSearchRepository userShippingSearchRepository;

    public UserShippingServiceImpl(UserShippingRepository userShippingRepository, UserShippingSearchRepository userShippingSearchRepository) {
        this.userShippingRepository = userShippingRepository;
        this.userShippingSearchRepository = userShippingSearchRepository;
    }

    /**
     * Save a userShipping.
     *
     * @param userShipping the entity to save
     * @return the persisted entity
     */
    @Override
    public UserShipping save(UserShipping userShipping) {
        log.debug("Request to save UserShipping : {}", userShipping);
        UserShipping result = userShippingRepository.save(userShipping);
        userShippingSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the userShippings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserShipping> findAll(Pageable pageable) {
        log.debug("Request to get all UserShippings");
        return userShippingRepository.findAll(pageable);
    }

    /**
     * Get one userShipping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserShipping findOne(Long id) {
        log.debug("Request to get UserShipping : {}", id);
        return userShippingRepository.findOne(id);
    }

    /**
     * Delete the userShipping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserShipping : {}", id);
        userShippingRepository.delete(id);
        userShippingSearchRepository.delete(id);
    }

    /**
     * Search for the userShipping corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserShipping> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserShippings for query {}", query);
        Page<UserShipping> result = userShippingSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
