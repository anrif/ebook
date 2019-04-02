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

import com.acumen.article.domain.ShippingAddress;
import com.acumen.article.domain.*; // for static metamodels
import com.acumen.article.repository.ShippingAddressRepository;
import com.acumen.article.repository.search.ShippingAddressSearchRepository;
import com.acumen.article.service.dto.ShippingAddressCriteria;


/**
 * Service for executing complex queries for ShippingAddress entities in the database.
 * The main input is a {@link ShippingAddressCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ShippingAddress} or a {@link Page} of {@link ShippingAddress} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShippingAddressQueryService extends QueryService<ShippingAddress> {

    private final Logger log = LoggerFactory.getLogger(ShippingAddressQueryService.class);


    private final ShippingAddressRepository shippingAddressRepository;

    private final ShippingAddressSearchRepository shippingAddressSearchRepository;

    public ShippingAddressQueryService(ShippingAddressRepository shippingAddressRepository, ShippingAddressSearchRepository shippingAddressSearchRepository) {
        this.shippingAddressRepository = shippingAddressRepository;
        this.shippingAddressSearchRepository = shippingAddressSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ShippingAddress} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ShippingAddress> findByCriteria(ShippingAddressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ShippingAddress> specification = createSpecification(criteria);
        return shippingAddressRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ShippingAddress} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShippingAddress> findByCriteria(ShippingAddressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ShippingAddress> specification = createSpecification(criteria);
        return shippingAddressRepository.findAll(specification, page);
    }

    /**
     * Function to convert ShippingAddressCriteria to a {@link Specifications}
     */
    private Specifications<ShippingAddress> createSpecification(ShippingAddressCriteria criteria) {
        Specifications<ShippingAddress> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ShippingAddress_.id));
            }
            if (criteria.getShippingAddressName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShippingAddressName(), ShippingAddress_.shippingAddressName));
            }
            if (criteria.getShippingAddressStreet1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShippingAddressStreet1(), ShippingAddress_.shippingAddressStreet1));
            }
            if (criteria.getShippingAddressStreet2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShippingAddressStreet2(), ShippingAddress_.shippingAddressStreet2));
            }
            if (criteria.getShippingAddressCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShippingAddressCity(), ShippingAddress_.shippingAddressCity));
            }
            if (criteria.getShippingAddressState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShippingAddressState(), ShippingAddress_.shippingAddressState));
            }
            if (criteria.getShippingAddressCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShippingAddressCountry(), ShippingAddress_.shippingAddressCountry));
            }
            if (criteria.getShippingAddressZipcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShippingAddressZipcode(), ShippingAddress_.shippingAddressZipcode));
            }
            if (criteria.getOrderedId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOrderedId(), ShippingAddress_.ordereds, Ordered_.id));
            }
        }
        return specification;
    }

}
