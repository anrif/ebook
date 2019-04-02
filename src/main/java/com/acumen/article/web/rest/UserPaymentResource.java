package com.acumen.article.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.acumen.article.domain.UserPayment;
import com.acumen.article.service.UserPaymentService;
import com.acumen.article.web.rest.errors.BadRequestAlertException;
import com.acumen.article.web.rest.util.HeaderUtil;
import com.acumen.article.web.rest.util.PaginationUtil;
import com.acumen.article.service.dto.UserPaymentCriteria;
import com.acumen.article.service.UserPaymentQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing UserPayment.
 */
@RestController
@RequestMapping("/api")
public class UserPaymentResource {

    private final Logger log = LoggerFactory.getLogger(UserPaymentResource.class);

    private static final String ENTITY_NAME = "userPayment";

    private final UserPaymentService userPaymentService;

    private final UserPaymentQueryService userPaymentQueryService;

    public UserPaymentResource(UserPaymentService userPaymentService, UserPaymentQueryService userPaymentQueryService) {
        this.userPaymentService = userPaymentService;
        this.userPaymentQueryService = userPaymentQueryService;
    }

    /**
     * POST  /user-payments : Create a new userPayment.
     *
     * @param userPayment the userPayment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userPayment, or with status 400 (Bad Request) if the userPayment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-payments")
    @Timed
    public ResponseEntity<UserPayment> createUserPayment(@Valid @RequestBody UserPayment userPayment) throws URISyntaxException {
        log.debug("REST request to save UserPayment : {}", userPayment);
        if (userPayment.getId() != null) {
            throw new BadRequestAlertException("A new userPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPayment result = userPaymentService.save(userPayment);
        return ResponseEntity.created(new URI("/api/user-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-payments : Updates an existing userPayment.
     *
     * @param userPayment the userPayment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userPayment,
     * or with status 400 (Bad Request) if the userPayment is not valid,
     * or with status 500 (Internal Server Error) if the userPayment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-payments")
    @Timed
    public ResponseEntity<UserPayment> updateUserPayment(@Valid @RequestBody UserPayment userPayment) throws URISyntaxException {
        log.debug("REST request to update UserPayment : {}", userPayment);
        if (userPayment.getId() == null) {
            return createUserPayment(userPayment);
        }
        UserPayment result = userPaymentService.save(userPayment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userPayment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-payments : get all the userPayments.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of userPayments in body
     */
    @GetMapping("/user-payments")
    @Timed
    public ResponseEntity<List<UserPayment>> getAllUserPayments(UserPaymentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserPayments by criteria: {}", criteria);
        Page<UserPayment> page = userPaymentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-payments/:id : get the "id" userPayment.
     *
     * @param id the id of the userPayment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userPayment, or with status 404 (Not Found)
     */
    @GetMapping("/user-payments/{id}")
    @Timed
    public ResponseEntity<UserPayment> getUserPayment(@PathVariable Long id) {
        log.debug("REST request to get UserPayment : {}", id);
        UserPayment userPayment = userPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userPayment));
    }

    /**
     * DELETE  /user-payments/:id : delete the "id" userPayment.
     *
     * @param id the id of the userPayment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-payments/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserPayment(@PathVariable Long id) {
        log.debug("REST request to delete UserPayment : {}", id);
        userPaymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-payments?query=:query : search for the userPayment corresponding
     * to the query.
     *
     * @param query the query of the userPayment search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/user-payments")
    @Timed
    public ResponseEntity<List<UserPayment>> searchUserPayments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserPayments for query {}", query);
        Page<UserPayment> page = userPaymentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/user-payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
