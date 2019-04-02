package com.acumen.article.service.impl;

import com.acumen.article.service.BillingAddressService;
import com.acumen.article.domain.BillingAddress;
import com.acumen.article.repository.BillingAddressRepository;
import com.acumen.article.repository.search.BillingAddressSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BillingAddress.
 */
@Service
@Transactional
public class BillingAddressServiceImpl implements BillingAddressService {

    private final Logger log = LoggerFactory.getLogger(BillingAddressServiceImpl.class);

    private final BillingAddressRepository billingAddressRepository;

    private final BillingAddressSearchRepository billingAddressSearchRepository;

    public BillingAddressServiceImpl(BillingAddressRepository billingAddressRepository, BillingAddressSearchRepository billingAddressSearchRepository) {
        this.billingAddressRepository = billingAddressRepository;
        this.billingAddressSearchRepository = billingAddressSearchRepository;
    }

    /**
     * Save a billingAddress.
     *
     * @param billingAddress the entity to save
     * @return the persisted entity
     */
    @Override
    public BillingAddress save(BillingAddress billingAddress) {
        log.debug("Request to save BillingAddress : {}", billingAddress);
        BillingAddress result = billingAddressRepository.save(billingAddress);
        billingAddressSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the billingAddresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BillingAddress> findAll(Pageable pageable) {
        log.debug("Request to get all BillingAddresses");
        return billingAddressRepository.findAll(pageable);
    }

    /**
     * Get one billingAddress by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BillingAddress findOne(Long id) {
        log.debug("Request to get BillingAddress : {}", id);
        return billingAddressRepository.findOne(id);
    }

    /**
     * Delete the billingAddress by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BillingAddress : {}", id);
        billingAddressRepository.delete(id);
        billingAddressSearchRepository.delete(id);
    }

    /**
     * Search for the billingAddress corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BillingAddress> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BillingAddresses for query {}", query);
        Page<BillingAddress> result = billingAddressSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
