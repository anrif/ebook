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

import com.acumen.article.domain.UserBilling;
import com.acumen.article.domain.*; // for static metamodels
import com.acumen.article.repository.UserBillingRepository;
import com.acumen.article.repository.search.UserBillingSearchRepository;
import com.acumen.article.service.dto.UserBillingCriteria;


/**
 * Service for executing complex queries for UserBilling entities in the database.
 * The main input is a {@link UserBillingCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserBilling} or a {@link Page} of {@link UserBilling} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserBillingQueryService extends QueryService<UserBilling> {

    private final Logger log = LoggerFactory.getLogger(UserBillingQueryService.class);


    private final UserBillingRepository userBillingRepository;

    private final UserBillingSearchRepository userBillingSearchRepository;

    public UserBillingQueryService(UserBillingRepository userBillingRepository, UserBillingSearchRepository userBillingSearchRepository) {
        this.userBillingRepository = userBillingRepository;
        this.userBillingSearchRepository = userBillingSearchRepository;
    }

    /**
     * Return a {@link List} of {@link UserBilling} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserBilling> findByCriteria(UserBillingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<UserBilling> specification = createSpecification(criteria);
        return userBillingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserBilling} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserBilling> findByCriteria(UserBillingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<UserBilling> specification = createSpecification(criteria);
        return userBillingRepository.findAll(specification, page);
    }

    /**
     * Function to convert UserBillingCriteria to a {@link Specifications}
     */
    private Specifications<UserBilling> createSpecification(UserBillingCriteria criteria) {
        Specifications<UserBilling> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserBilling_.id));
            }
            if (criteria.getUserBillingName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserBillingName(), UserBilling_.userBillingName));
            }
            if (criteria.getUserBillingStreet1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserBillingStreet1(), UserBilling_.userBillingStreet1));
            }
            if (criteria.getUserBillingStreet2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserBillingStreet2(), UserBilling_.userBillingStreet2));
            }
            if (criteria.getUserBillingCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserBillingCity(), UserBilling_.userBillingCity));
            }
            if (criteria.getUserBillingState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserBillingState(), UserBilling_.userBillingState));
            }
            if (criteria.getUserBillingCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserBillingCountry(), UserBilling_.userBillingCountry));
            }
            if (criteria.getUserBillingZipcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserBillingZipcode(), UserBilling_.userBillingZipcode));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), UserBilling_.user, User_.id));
            }
        }
        return specification;
    }

}
