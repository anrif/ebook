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

import com.acumen.article.domain.ShoppingCart;
import com.acumen.article.domain.*; // for static metamodels
import com.acumen.article.repository.ShoppingCartRepository;
import com.acumen.article.repository.search.ShoppingCartSearchRepository;
import com.acumen.article.service.dto.ShoppingCartCriteria;


/**
 * Service for executing complex queries for ShoppingCart entities in the database.
 * The main input is a {@link ShoppingCartCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ShoppingCart} or a {@link Page} of {@link ShoppingCart} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShoppingCartQueryService extends QueryService<ShoppingCart> {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartQueryService.class);


    private final ShoppingCartRepository shoppingCartRepository;

    private final ShoppingCartSearchRepository shoppingCartSearchRepository;

    public ShoppingCartQueryService(ShoppingCartRepository shoppingCartRepository, ShoppingCartSearchRepository shoppingCartSearchRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartSearchRepository = shoppingCartSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ShoppingCart} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ShoppingCart> findByCriteria(ShoppingCartCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ShoppingCart> specification = createSpecification(criteria);
        return shoppingCartRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ShoppingCart} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShoppingCart> findByCriteria(ShoppingCartCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ShoppingCart> specification = createSpecification(criteria);
        return shoppingCartRepository.findAll(specification, page);
    }

    /**
     * Function to convert ShoppingCartCriteria to a {@link Specifications}
     */
    private Specifications<ShoppingCart> createSpecification(ShoppingCartCriteria criteria) {
        Specifications<ShoppingCart> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ShoppingCart_.id));
            }
            if (criteria.getGrandTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrandTotal(), ShoppingCart_.grandTotal));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), ShoppingCart_.user, User_.id));
            }
        }
        return specification;
    }

}
