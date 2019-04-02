package com.acumen.article.service.impl;

import com.acumen.article.service.CartItemService;
import com.acumen.article.domain.CartItem;
import com.acumen.article.repository.CartItemRepository;
import com.acumen.article.repository.search.CartItemSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CartItem.
 */
@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

    private final Logger log = LoggerFactory.getLogger(CartItemServiceImpl.class);

    private final CartItemRepository cartItemRepository;

    private final CartItemSearchRepository cartItemSearchRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartItemSearchRepository cartItemSearchRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemSearchRepository = cartItemSearchRepository;
    }

    /**
     * Save a cartItem.
     *
     * @param cartItem the entity to save
     * @return the persisted entity
     */
    @Override
    public CartItem save(CartItem cartItem) {
        log.debug("Request to save CartItem : {}", cartItem);
        CartItem result = cartItemRepository.save(cartItem);
        cartItemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the cartItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CartItem> findAll(Pageable pageable) {
        log.debug("Request to get all CartItems");
        return cartItemRepository.findAll(pageable);
    }

    /**
     * Get one cartItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CartItem findOne(Long id) {
        log.debug("Request to get CartItem : {}", id);
        return cartItemRepository.findOne(id);
    }

    /**
     * Delete the cartItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CartItem : {}", id);
        cartItemRepository.delete(id);
        cartItemSearchRepository.delete(id);
    }

    /**
     * Search for the cartItem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CartItem> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CartItems for query {}", query);
        Page<CartItem> result = cartItemSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
