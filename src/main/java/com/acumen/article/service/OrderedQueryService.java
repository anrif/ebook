package com.acumen.article.service;

import java.time.LocalDate;
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

import com.acumen.article.domain.Ordered;
import com.acumen.article.domain.*; // for static metamodels
import com.acumen.article.repository.OrderedRepository;
import com.acumen.article.repository.search.OrderedSearchRepository;
import com.acumen.article.service.dto.OrderedCriteria;


/**
 * Service for executing complex queries for Ordered entities in the database.
 * The main input is a {@link OrderedCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Ordered} or a {@link Page} of {@link Ordered} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrderedQueryService extends QueryService<Ordered> {

    private final Logger log = LoggerFactory.getLogger(OrderedQueryService.class);


    private final OrderedRepository orderedRepository;

    private final OrderedSearchRepository orderedSearchRepository;

    public OrderedQueryService(OrderedRepository orderedRepository, OrderedSearchRepository orderedSearchRepository) {
        this.orderedRepository = orderedRepository;
        this.orderedSearchRepository = orderedSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Ordered} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Ordered> findByCriteria(OrderedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Ordered> specification = createSpecification(criteria);
        return orderedRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Ordered} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Ordered> findByCriteria(OrderedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Ordered> specification = createSpecification(criteria);
        return orderedRepository.findAll(specification, page);
    }

    /**
     * Function to convert OrderedCriteria to a {@link Specifications}
     */
    private Specifications<Ordered> createSpecification(OrderedCriteria criteria) {
        Specifications<Ordered> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Ordered_.id));
            }
            if (criteria.getOrderDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderDate(), Ordered_.orderDate));
            }
            if (criteria.getShippingDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShippingDate(), Ordered_.shippingDate));
            }
            if (criteria.getShippingMethod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShippingMethod(), Ordered_.shippingMethod));
            }
            if (criteria.getOrderStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderStatus(), Ordered_.orderStatus));
            }
            if (criteria.getOrderTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderTotal(), Ordered_.orderTotal));
            }
            if (criteria.getShippingAddresssId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getShippingAddresssId(), Ordered_.shippingAddressses, ShippingAddress_.id));
            }
            if (criteria.getBillingAddresssId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getBillingAddresssId(), Ordered_.billingAddressses, BillingAddress_.id));
            }
            if (criteria.getPaymentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPaymentId(), Ordered_.payments, Payment_.id));
            }
        }
        return specification;
    }

}
