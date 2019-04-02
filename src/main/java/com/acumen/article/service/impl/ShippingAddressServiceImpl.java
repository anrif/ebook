package com.acumen.article.service.impl;

import com.acumen.article.service.ShippingAddressService;
import com.acumen.article.domain.ShippingAddress;
import com.acumen.article.repository.ShippingAddressRepository;
import com.acumen.article.repository.search.ShippingAddressSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ShippingAddress.
 */
@Service
@Transactional
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final Logger log = LoggerFactory.getLogger(ShippingAddressServiceImpl.class);

    private final ShippingAddressRepository shippingAddressRepository;

    private final ShippingAddressSearchRepository shippingAddressSearchRepository;

    public ShippingAddressServiceImpl(ShippingAddressRepository shippingAddressRepository, ShippingAddressSearchRepository shippingAddressSearchRepository) {
        this.shippingAddressRepository = shippingAddressRepository;
        this.shippingAddressSearchRepository = shippingAddressSearchRepository;
    }

    /**
     * Save a shippingAddress.
     *
     * @param shippingAddress the entity to save
     * @return the persisted entity
     */
    @Override
    public ShippingAddress save(ShippingAddress shippingAddress) {
        log.debug("Request to save ShippingAddress : {}", shippingAddress);
        ShippingAddress result = shippingAddressRepository.save(shippingAddress);
        shippingAddressSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the shippingAddresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShippingAddress> findAll(Pageable pageable) {
        log.debug("Request to get all ShippingAddresses");
        return shippingAddressRepository.findAll(pageable);
    }

    /**
     * Get one shippingAddress by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShippingAddress findOne(Long id) {
        log.debug("Request to get ShippingAddress : {}", id);
        return shippingAddressRepository.findOne(id);
    }

    /**
     * Delete the shippingAddress by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShippingAddress : {}", id);
        shippingAddressRepository.delete(id);
        shippingAddressSearchRepository.delete(id);
    }

    /**
     * Search for the shippingAddress corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShippingAddress> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ShippingAddresses for query {}", query);
        Page<ShippingAddress> result = shippingAddressSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
