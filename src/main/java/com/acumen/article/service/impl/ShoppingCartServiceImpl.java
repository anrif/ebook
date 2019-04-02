package com.acumen.article.service.impl;

import com.acumen.article.service.ShoppingCartService;
import com.acumen.article.domain.ShoppingCart;
import com.acumen.article.repository.ShoppingCartRepository;
import com.acumen.article.repository.search.ShoppingCartSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ShoppingCart.
 */
@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    private final ShoppingCartRepository shoppingCartRepository;

    private final ShoppingCartSearchRepository shoppingCartSearchRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ShoppingCartSearchRepository shoppingCartSearchRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartSearchRepository = shoppingCartSearchRepository;
    }

    /**
     * Save a shoppingCart.
     *
     * @param shoppingCart the entity to save
     * @return the persisted entity
     */
    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        log.debug("Request to save ShoppingCart : {}", shoppingCart);
        ShoppingCart result = shoppingCartRepository.save(shoppingCart);
        shoppingCartSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the shoppingCarts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShoppingCart> findAll(Pageable pageable) {
        log.debug("Request to get all ShoppingCarts");
        return shoppingCartRepository.findAll(pageable);
    }

    /**
     * Get one shoppingCart by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShoppingCart findOne(Long id) {
        log.debug("Request to get ShoppingCart : {}", id);
        return shoppingCartRepository.findOne(id);
    }

    /**
     * Delete the shoppingCart by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoppingCart : {}", id);
        shoppingCartRepository.delete(id);
        shoppingCartSearchRepository.delete(id);
    }

    /**
     * Search for the shoppingCart corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShoppingCart> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ShoppingCarts for query {}", query);
        Page<ShoppingCart> result = shoppingCartSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
