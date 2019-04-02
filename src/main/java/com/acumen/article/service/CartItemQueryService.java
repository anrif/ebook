package com.acumen.article.service;


import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.acumen.article.domain.CartItem;
import com.acumen.article.domain.*; // for static metamodels
import com.acumen.article.repository.CartItemRepository;
import com.acumen.article.repository.search.CartItemSearchRepository;
import com.acumen.article.service.dto.CartItemCriteria;


/**
 * Service for executing complex queries for CartItem entities in the database.
 * The main input is a {@link CartItemCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CartItem} or a {@link Page} of {@link CartItem} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CartItemQueryService extends QueryService<CartItem> {

    private final Logger log = LoggerFactory.getLogger(CartItemQueryService.class);


    private final CartItemRepository cartItemRepository;

    private final CartItemSearchRepository cartItemSearchRepository;

    public CartItemQueryService(CartItemRepository cartItemRepository, CartItemSearchRepository cartItemSearchRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemSearchRepository = cartItemSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CartItem} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CartItem> findByCriteria(CartItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CartItem> specification = createSpecification(criteria);
        return cartItemRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CartItem} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CartItem> findByCriteria(CartItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CartItem> specification = createSpecification(criteria);
        return cartItemRepository.findAll(specification, page);
    }

    /**
     * Function to convert CartItemCriteria to a {@link Specifications}
     */
    private Specifications<CartItem> createSpecification(CartItemCriteria criteria) {
        Specifications<CartItem> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CartItem_.id));
            }
            if (criteria.getQty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQty(), CartItem_.qty));
            }
            if (criteria.getSubtotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubtotal(), CartItem_.subtotal));
            }
            if (criteria.getBookId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getBookId(), CartItem_.book, Book_.id));
            }
            if (criteria.getShoppingCartId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getShoppingCartId(), CartItem_.shoppingCart, ShoppingCart_.id));
            }
            if (criteria.getOrderedId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOrderedId(), CartItem_.ordered, Ordered_.id));
            }
        }
        return specification;
    }

}
