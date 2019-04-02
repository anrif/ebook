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

import com.acumen.article.domain.Payment;
import com.acumen.article.domain.*; // for static metamodels
import com.acumen.article.repository.PaymentRepository;
import com.acumen.article.repository.search.PaymentSearchRepository;
import com.acumen.article.service.dto.PaymentCriteria;


/**
 * Service for executing complex queries for Payment entities in the database.
 * The main input is a {@link PaymentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Payment} or a {@link Page} of {@link Payment} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentQueryService extends QueryService<Payment> {

    private final Logger log = LoggerFactory.getLogger(PaymentQueryService.class);


    private final PaymentRepository paymentRepository;

    private final PaymentSearchRepository paymentSearchRepository;

    public PaymentQueryService(PaymentRepository paymentRepository, PaymentSearchRepository paymentSearchRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentSearchRepository = paymentSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Payment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Payment> findByCriteria(PaymentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Payment> specification = createSpecification(criteria);
        return paymentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Payment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Payment> findByCriteria(PaymentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Payment> specification = createSpecification(criteria);
        return paymentRepository.findAll(specification, page);
    }

    /**
     * Function to convert PaymentCriteria to a {@link Specifications}
     */
    private Specifications<Payment> createSpecification(PaymentCriteria criteria) {
        Specifications<Payment> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Payment_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Payment_.type));
            }
            if (criteria.getCartName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCartName(), Payment_.cartName));
            }
            if (criteria.getCardNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCardNumber(), Payment_.cardNumber));
            }
            if (criteria.getExpiryMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryMonth(), Payment_.expiryMonth));
            }
            if (criteria.getExpiryYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryYear(), Payment_.expiryYear));
            }
            if (criteria.getCvc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCvc(), Payment_.cvc));
            }
            if (criteria.getHolderName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHolderName(), Payment_.holderName));
            }
            if (criteria.getOrderedId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOrderedId(), Payment_.ordereds, Ordered_.id));
            }
        }
        return specification;
    }

}
