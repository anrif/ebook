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

import com.acumen.article.domain.UserShipping;
import com.acumen.article.domain.*; // for static metamodels
import com.acumen.article.repository.UserShippingRepository;
import com.acumen.article.repository.search.UserShippingSearchRepository;
import com.acumen.article.service.dto.UserShippingCriteria;


/**
 * Service for executing complex queries for UserShipping entities in the database.
 * The main input is a {@link UserShippingCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserShipping} or a {@link Page} of {@link UserShipping} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserShippingQueryService extends QueryService<UserShipping> {

    private final Logger log = LoggerFactory.getLogger(UserShippingQueryService.class);


    private final UserShippingRepository userShippingRepository;

    private final UserShippingSearchRepository userShippingSearchRepository;

    public UserShippingQueryService(UserShippingRepository userShippingRepository, UserShippingSearchRepository userShippingSearchRepository) {
        this.userShippingRepository = userShippingRepository;
        this.userShippingSearchRepository = userShippingSearchRepository;
    }

    /**
     * Return a {@link List} of {@link UserShipping} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserShipping> findByCriteria(UserShippingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<UserShipping> specification = createSpecification(criteria);
        return userShippingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserShipping} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserShipping> findByCriteria(UserShippingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<UserShipping> specification = createSpecification(criteria);
        return userShippingRepository.findAll(specification, page);
    }

    /**
     * Function to convert UserShippingCriteria to a {@link Specifications}
     */
    private Specifications<UserShipping> createSpecification(UserShippingCriteria criteria) {
        Specifications<UserShipping> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserShipping_.id));
            }
            if (criteria.getUserShippingName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserShippingName(), UserShipping_.userShippingName));
            }
            if (criteria.getUserShippingStreet1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserShippingStreet1(), UserShipping_.userShippingStreet1));
            }
            if (criteria.getUserShippingStreet2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserShippingStreet2(), UserShipping_.userShippingStreet2));
            }
            if (criteria.getUserShippingCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserShippingCity(), UserShipping_.userShippingCity));
            }
            if (criteria.getUserShippingState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserShippingState(), UserShipping_.userShippingState));
            }
            if (criteria.getUserShippingCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserShippingCountry(), UserShipping_.userShippingCountry));
            }
            if (criteria.getUserShippingZipcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserShippingZipcode(), UserShipping_.userShippingZipcode));
            }
            if (criteria.getUserShippingDefault() != null) {
                specification = specification.and(buildSpecification(criteria.getUserShippingDefault(), UserShipping_.userShippingDefault));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), UserShipping_.user, User_.id));
            }
        }
        return specification;
    }

}
