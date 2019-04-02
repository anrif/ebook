package com.acumen.article.service.impl;

import com.acumen.article.service.UserBillingService;
import com.acumen.article.domain.UserBilling;
import com.acumen.article.repository.UserBillingRepository;
import com.acumen.article.repository.search.UserBillingSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UserBilling.
 */
@Service
@Transactional
public class UserBillingServiceImpl implements UserBillingService {

    private final Logger log = LoggerFactory.getLogger(UserBillingServiceImpl.class);

    private final UserBillingRepository userBillingRepository;

    private final UserBillingSearchRepository userBillingSearchRepository;

    public UserBillingServiceImpl(UserBillingRepository userBillingRepository, UserBillingSearchRepository userBillingSearchRepository) {
        this.userBillingRepository = userBillingRepository;
        this.userBillingSearchRepository = userBillingSearchRepository;
    }

    /**
     * Save a userBilling.
     *
     * @param userBilling the entity to save
     * @return the persisted entity
     */
    @Override
    public UserBilling save(UserBilling userBilling) {
        log.debug("Request to save UserBilling : {}", userBilling);
        UserBilling result = userBillingRepository.save(userBilling);
        userBillingSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the userBillings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserBilling> findAll(Pageable pageable) {
        log.debug("Request to get all UserBillings");
        return userBillingRepository.findAll(pageable);
    }

    /**
     * Get one userBilling by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserBilling findOne(Long id) {
        log.debug("Request to get UserBilling : {}", id);
        return userBillingRepository.findOne(id);
    }

    /**
     * Delete the userBilling by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserBilling : {}", id);
        userBillingRepository.delete(id);
        userBillingSearchRepository.delete(id);
    }

    /**
     * Search for the userBilling corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserBilling> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserBillings for query {}", query);
        Page<UserBilling> result = userBillingSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
