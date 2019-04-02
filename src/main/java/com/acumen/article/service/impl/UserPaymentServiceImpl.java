package com.acumen.article.service.impl;

import com.acumen.article.service.UserPaymentService;
import com.acumen.article.domain.UserPayment;
import com.acumen.article.repository.UserPaymentRepository;
import com.acumen.article.repository.search.UserPaymentSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UserPayment.
 */
@Service
@Transactional
public class UserPaymentServiceImpl implements UserPaymentService {

    private final Logger log = LoggerFactory.getLogger(UserPaymentServiceImpl.class);

    private final UserPaymentRepository userPaymentRepository;

    private final UserPaymentSearchRepository userPaymentSearchRepository;

    public UserPaymentServiceImpl(UserPaymentRepository userPaymentRepository, UserPaymentSearchRepository userPaymentSearchRepository) {
        this.userPaymentRepository = userPaymentRepository;
        this.userPaymentSearchRepository = userPaymentSearchRepository;
    }

    /**
     * Save a userPayment.
     *
     * @param userPayment the entity to save
     * @return the persisted entity
     */
    @Override
    public UserPayment save(UserPayment userPayment) {
        log.debug("Request to save UserPayment : {}", userPayment);
        UserPayment result = userPaymentRepository.save(userPayment);
        userPaymentSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the userPayments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserPayment> findAll(Pageable pageable) {
        log.debug("Request to get all UserPayments");
        return userPaymentRepository.findAll(pageable);
    }

    /**
     * Get one userPayment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserPayment findOne(Long id) {
        log.debug("Request to get UserPayment : {}", id);
        return userPaymentRepository.findOne(id);
    }

    /**
     * Delete the userPayment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserPayment : {}", id);
        userPaymentRepository.delete(id);
        userPaymentSearchRepository.delete(id);
    }

    /**
     * Search for the userPayment corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserPayment> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserPayments for query {}", query);
        Page<UserPayment> result = userPaymentSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
