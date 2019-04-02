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

import com.acumen.article.domain.Book;
import com.acumen.article.domain.*; // for static metamodels
import com.acumen.article.repository.BookRepository;
import com.acumen.article.repository.search.BookSearchRepository;
import com.acumen.article.service.dto.BookCriteria;

import com.acumen.article.domain.enumeration.Language;
import com.acumen.article.domain.enumeration.BookCategory;
import com.acumen.article.domain.enumeration.BookFormat;

/**
 * Service for executing complex queries for Book entities in the database.
 * The main input is a {@link BookCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Book} or a {@link Page} of {@link Book} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BookQueryService extends QueryService<Book> {

    private final Logger log = LoggerFactory.getLogger(BookQueryService.class);


    private final BookRepository bookRepository;

    private final BookSearchRepository bookSearchRepository;

    public BookQueryService(BookRepository bookRepository, BookSearchRepository bookSearchRepository) {
        this.bookRepository = bookRepository;
        this.bookSearchRepository = bookSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Book} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Book> findByCriteria(BookCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Book> specification = createSpecification(criteria);
        return bookRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Book} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Book> findByCriteria(BookCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Book> specification = createSpecification(criteria);
        return bookRepository.findAll(specification, page);
    }

    /**
     * Function to convert BookCriteria to a {@link Specifications}
     */
    private Specifications<Book> createSpecification(BookCriteria criteria) {
        Specifications<Book> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Book_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Book_.title));
            }
            if (criteria.getAuthor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthor(), Book_.author));
            }
            if (criteria.getPublisher() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPublisher(), Book_.publisher));
            }
            if (criteria.getPublicationDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPublicationDate(), Book_.publicationDate));
            }
            if (criteria.getLanguage() != null) {
                specification = specification.and(buildSpecification(criteria.getLanguage(), Book_.language));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getCategory(), Book_.category));
            }
            if (criteria.getNumberOfPages() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfPages(), Book_.numberOfPages));
            }
            if (criteria.getFormat() != null) {
                specification = specification.and(buildSpecification(criteria.getFormat(), Book_.format));
            }
            if (criteria.getIsbn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsbn(), Book_.isbn));
            }
            if (criteria.getShippingWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShippingWeight(), Book_.shippingWeight));
            }
            if (criteria.getLastPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastPrice(), Book_.lastPrice));
            }
            if (criteria.getOurPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOurPrice(), Book_.ourPrice));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Book_.active));
            }
            if (criteria.getInStockNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInStockNumber(), Book_.inStockNumber));
            }
        }
        return specification;
    }

}
