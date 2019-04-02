package com.acumen.article.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.acumen.article.domain.UserShipping;
import com.acumen.article.service.UserShippingService;
import com.acumen.article.web.rest.errors.BadRequestAlertException;
import com.acumen.article.web.rest.util.HeaderUtil;
import com.acumen.article.web.rest.util.PaginationUtil;
import com.acumen.article.service.dto.UserShippingCriteria;
import com.acumen.article.service.UserShippingQueryService;
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
 * REST controller for managing UserShipping.
 */
@RestController
@RequestMapping("/api")
public class UserShippingResource {

    private final Logger log = LoggerFactory.getLogger(UserShippingResource.class);

    private static final String ENTITY_NAME = "userShipping";

    private final UserShippingService userShippingService;

    private final UserShippingQueryService userShippingQueryService;

    public UserShippingResource(UserShippingService userShippingService, UserShippingQueryService userShippingQueryService) {
        this.userShippingService = userShippingService;
        this.userShippingQueryService = userShippingQueryService;
    }

    /**
     * POST  /user-shippings : Create a new userShipping.
     *
     * @param userShipping the userShipping to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userShipping, or with status 400 (Bad Request) if the userShipping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-shippings")
    @Timed
    public ResponseEntity<UserShipping> createUserShipping(@Valid @RequestBody UserShipping userShipping) throws URISyntaxException {
        log.debug("REST request to save UserShipping : {}", userShipping);
        if (userShipping.getId() != null) {
            throw new BadRequestAlertException("A new userShipping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserShipping result = userShippingService.save(userShipping);
        return ResponseEntity.created(new URI("/api/user-shippings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-shippings : Updates an existing userShipping.
     *
     * @param userShipping the userShipping to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userShipping,
     * or with status 400 (Bad Request) if the userShipping is not valid,
     * or with status 500 (Internal Server Error) if the userShipping couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-shippings")
    @Timed
    public ResponseEntity<UserShipping> updateUserShipping(@Valid @RequestBody UserShipping userShipping) throws URISyntaxException {
        log.debug("REST request to update UserShipping : {}", userShipping);
        if (userShipping.getId() == null) {
            return createUserShipping(userShipping);
        }
        UserShipping result = userShippingService.save(userShipping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userShipping.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-shippings : get all the userShippings.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of userShippings in body
     */
    @GetMapping("/user-shippings")
    @Timed
    public ResponseEntity<List<UserShipping>> getAllUserShippings(UserShippingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserShippings by criteria: {}", criteria);
        Page<UserShipping> page = userShippingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-shippings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-shippings/:id : get the "id" userShipping.
     *
     * @param id the id of the userShipping to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userShipping, or with status 404 (Not Found)
     */
    @GetMapping("/user-shippings/{id}")
    @Timed
    public ResponseEntity<UserShipping> getUserShipping(@PathVariable Long id) {
        log.debug("REST request to get UserShipping : {}", id);
        UserShipping userShipping = userShippingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userShipping));
    }

    /**
     * DELETE  /user-shippings/:id : delete the "id" userShipping.
     *
     * @param id the id of the userShipping to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-shippings/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserShipping(@PathVariable Long id) {
        log.debug("REST request to delete UserShipping : {}", id);
        userShippingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-shippings?query=:query : search for the userShipping corresponding
     * to the query.
     *
     * @param query the query of the userShipping search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/user-shippings")
    @Timed
    public ResponseEntity<List<UserShipping>> searchUserShippings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserShippings for query {}", query);
        Page<UserShipping> page = userShippingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/user-shippings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
