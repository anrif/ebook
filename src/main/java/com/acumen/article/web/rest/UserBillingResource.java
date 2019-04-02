package com.acumen.article.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.acumen.article.domain.UserBilling;
import com.acumen.article.service.UserBillingService;
import com.acumen.article.web.rest.errors.BadRequestAlertException;
import com.acumen.article.web.rest.util.HeaderUtil;
import com.acumen.article.web.rest.util.PaginationUtil;
import com.acumen.article.service.dto.UserBillingCriteria;
import com.acumen.article.service.UserBillingQueryService;
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
 * REST controller for managing UserBilling.
 */
@RestController
@RequestMapping("/api")
public class UserBillingResource {

    private final Logger log = LoggerFactory.getLogger(UserBillingResource.class);

    private static final String ENTITY_NAME = "userBilling";

    private final UserBillingService userBillingService;

    private final UserBillingQueryService userBillingQueryService;

    public UserBillingResource(UserBillingService userBillingService, UserBillingQueryService userBillingQueryService) {
        this.userBillingService = userBillingService;
        this.userBillingQueryService = userBillingQueryService;
    }

    /**
     * POST  /user-billings : Create a new userBilling.
     *
     * @param userBilling the userBilling to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userBilling, or with status 400 (Bad Request) if the userBilling has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-billings")
    @Timed
    public ResponseEntity<UserBilling> createUserBilling(@Valid @RequestBody UserBilling userBilling) throws URISyntaxException {
        log.debug("REST request to save UserBilling : {}", userBilling);
        if (userBilling.getId() != null) {
            throw new BadRequestAlertException("A new userBilling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserBilling result = userBillingService.save(userBilling);
        return ResponseEntity.created(new URI("/api/user-billings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-billings : Updates an existing userBilling.
     *
     * @param userBilling the userBilling to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userBilling,
     * or with status 400 (Bad Request) if the userBilling is not valid,
     * or with status 500 (Internal Server Error) if the userBilling couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-billings")
    @Timed
    public ResponseEntity<UserBilling> updateUserBilling(@Valid @RequestBody UserBilling userBilling) throws URISyntaxException {
        log.debug("REST request to update UserBilling : {}", userBilling);
        if (userBilling.getId() == null) {
            return createUserBilling(userBilling);
        }
        UserBilling result = userBillingService.save(userBilling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userBilling.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-billings : get all the userBillings.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of userBillings in body
     */
    @GetMapping("/user-billings")
    @Timed
    public ResponseEntity<List<UserBilling>> getAllUserBillings(UserBillingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserBillings by criteria: {}", criteria);
        Page<UserBilling> page = userBillingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-billings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-billings/:id : get the "id" userBilling.
     *
     * @param id the id of the userBilling to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userBilling, or with status 404 (Not Found)
     */
    @GetMapping("/user-billings/{id}")
    @Timed
    public ResponseEntity<UserBilling> getUserBilling(@PathVariable Long id) {
        log.debug("REST request to get UserBilling : {}", id);
        UserBilling userBilling = userBillingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userBilling));
    }

    /**
     * DELETE  /user-billings/:id : delete the "id" userBilling.
     *
     * @param id the id of the userBilling to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-billings/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserBilling(@PathVariable Long id) {
        log.debug("REST request to delete UserBilling : {}", id);
        userBillingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-billings?query=:query : search for the userBilling corresponding
     * to the query.
     *
     * @param query the query of the userBilling search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/user-billings")
    @Timed
    public ResponseEntity<List<UserBilling>> searchUserBillings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserBillings for query {}", query);
        Page<UserBilling> page = userBillingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/user-billings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
