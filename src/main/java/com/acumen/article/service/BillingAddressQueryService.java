package com.acumen.article.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.acumen.article.domain.BillingAddress;
import com.acumen.article.domain.*; // for static metamodels
import com.acumen.article.repository.BillingAddressRepository;
import com.acumen.article.repository.search.BillingAddressSearchRepository;
import com.acumen.article.service.dto.BillingAddressCriteria;


/**
 * Service for executing complex queries for BillingAddress entities in the database.
 * The main input is a {@link BillingAddressCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BillingAddress} or a {@link Page} of {@link BillingAddress} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BillingAddressQueryService extends QueryService<BillingAddress> {

    private final Logger log = LoggerFactory.getLogger(BillingAddressQueryService.class);


    private final BillingAddressRepository billingAddressRepository;

    private final BillingAddressSearchRepository billingAddressSearchRepository;

    public BillingAddressQueryService(BillingAddressRepository billingAddressRepository, BillingAddressSearchRepository billingAddressSearchRepository) {
        this.billingAddressRepository = billingAddressRepository;
        this.billingAddressSearchRepository = billingAddressSearchRepository;
    }

    /**
     * Return a {@link List} of {@link BillingAddress} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BillingAddress> findByCriteria(BillingAddressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<BillingAddress> specification = createSpecification(criteria);
        return billingAddressRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BillingAddress} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BillingAddress> findByCriteria(BillingAddressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<BillingAddress> specification = createSpecification(criteria);
        return billingAddressRepository.findAll(specification, page);
    }

    /**
     * Function to convert BillingAddressCriteria to a {@link Specifications}
     */
    private Specifications<BillingAddress> createSpecification(BillingAddressCriteria criteria) {
        Specifications<BillingAddress> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BillingAddress_.id));
            }
            if (criteria.getBillingAddressName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillingAddressName(), BillingAddress_.billingAddressName));
            }
            if (criteria.getBillingAddressStreet1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillingAddressStreet1(), BillingAddress_.billingAddressStreet1));
            }
            if (criteria.getBillingAddressStreet2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillingAddressStreet2(), BillingAddress_.billingAddressStreet2));
            }
            if (criteria.getBillingAddressCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillingAddressCity(), BillingAddress_.billingAddressCity));
            }
            if (criteria.getBillingAddressState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillingAddressState(), BillingAddress_.billingAddressState));
            }
            if (criteria.getBillingAddressCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillingAddressCountry(), BillingAddress_.billingAddressCountry));
            }
            if (criteria.getBillingAddressZipcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillingAddressZipcode(), BillingAddress_.billingAddressZipcode));
            }
            if (criteria.getOrderedId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOrderedId(), BillingAddress_.ordereds, Ordered_.id));
            }
        }
        return specification;
    }

}
