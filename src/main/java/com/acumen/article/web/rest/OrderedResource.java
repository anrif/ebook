package com.acumen.article.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.acumen.article.domain.Ordered;
import com.acumen.article.service.OrderedService;
import com.acumen.article.web.rest.errors.BadRequestAlertException;
import com.acumen.article.web.rest.util.HeaderUtil;
import com.acumen.article.web.rest.util.PaginationUtil;
import com.acumen.article.service.dto.OrderedCriteria;
import com.acumen.article.service.OrderedQueryService;
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
 * REST controller for managing Ordered.
 */
@RestController
@RequestMapping("/api")
public class OrderedResource {

    private final Logger log = LoggerFactory.getLogger(OrderedResource.class);

    private static final String ENTITY_NAME = "ordered";

    private final OrderedService orderedService;

    private final OrderedQueryService orderedQueryService;

    public OrderedResource(OrderedService orderedService, OrderedQueryService orderedQueryService) {
        this.orderedService = orderedService;
        this.orderedQueryService = orderedQueryService;
    }

    /**
     * POST  /ordereds : Create a new ordered.
     *
     * @param ordered the ordered to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordered, or with status 400 (Bad Request) if the ordered has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ordereds")
    @Timed
    public ResponseEntity<Ordered> createOrdered(@Valid @RequestBody Ordered ordered) throws URISyntaxException {
        log.debug("REST request to save Ordered : {}", ordered);
        if (ordered.getId() != null) {
            throw new BadRequestAlertException("A new ordered cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ordered result = orderedService.save(ordered);
        return ResponseEntity.created(new URI("/api/ordereds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ordereds : Updates an existing ordered.
     *
     * @param ordered the ordered to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordered,
     * or with status 400 (Bad Request) if the ordered is not valid,
     * or with status 500 (Internal Server Error) if the ordered couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ordereds")
    @Timed
    public ResponseEntity<Ordered> updateOrdered(@Valid @RequestBody Ordered ordered) throws URISyntaxException {
        log.debug("REST request to update Ordered : {}", ordered);
        if (ordered.getId() == null) {
            return createOrdered(ordered);
        }
        Ordered result = orderedService.save(ordered);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordered.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ordereds : get all the ordereds.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of ordereds in body
     */
    @GetMapping("/ordereds")
    @Timed
    public ResponseEntity<List<Ordered>> getAllOrdereds(OrderedCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Ordereds by criteria: {}", criteria);
        Page<Ordered> page = orderedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ordereds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ordereds/:id : get the "id" ordered.
     *
     * @param id the id of the ordered to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordered, or with status 404 (Not Found)
     */
    @GetMapping("/ordereds/{id}")
    @Timed
    public ResponseEntity<Ordered> getOrdered(@PathVariable Long id) {
        log.debug("REST request to get Ordered : {}", id);
        Ordered ordered = orderedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ordered));
    }

    /**
     * DELETE  /ordereds/:id : delete the "id" ordered.
     *
     * @param id the id of the ordered to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ordereds/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdered(@PathVariable Long id) {
        log.debug("REST request to delete Ordered : {}", id);
        orderedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ordereds?query=:query : search for the ordered corresponding
     * to the query.
     *
     * @param query the query of the ordered search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ordereds")
    @Timed
    public ResponseEntity<List<Ordered>> searchOrdereds(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Ordereds for query {}", query);
        Page<Ordered> page = orderedService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ordereds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
