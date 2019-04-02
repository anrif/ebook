package com.acumen.article.service.impl;

import com.acumen.article.service.BookService;
import com.acumen.article.domain.Book;
import com.acumen.article.repository.BookRepository;
import com.acumen.article.repository.search.BookSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Book.
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    private final BookSearchRepository bookSearchRepository;

    public BookServiceImpl(BookRepository bookRepository, BookSearchRepository bookSearchRepository) {
        this.bookRepository = bookRepository;
        this.bookSearchRepository = bookSearchRepository;
    }

    /**
     * Save a book.
     *
     * @param book the entity to save
     * @return the persisted entity
     */
    @Override
    public Book save(Book book) {
        log.debug("Request to save Book : {}", book);
        Book result = bookRepository.save(book);
        bookSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the books.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Book> findAll(Pageable pageable) {
        log.debug("Request to get all Books");
        return bookRepository.findAll(pageable);
    }

    /**
     * Get one book by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Book findOne(Long id) {
        log.debug("Request to get Book : {}", id);
        return bookRepository.findOne(id);
    }

    /**
     * Delete the book by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Book : {}", id);
        bookRepository.delete(id);
        bookSearchRepository.delete(id);
    }

    /**
     * Search for the book corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Book> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Books for query {}", query);
        Page<Book> result = bookSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
