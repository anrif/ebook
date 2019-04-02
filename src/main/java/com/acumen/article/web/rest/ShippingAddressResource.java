package com.acumen.article.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.acumen.article.domain.ShippingAddress;
import com.acumen.article.service.ShippingAddressService;
import com.acumen.article.web.rest.errors.BadRequestAlertException;
import com.acumen.article.web.rest.util.HeaderUtil;
import com.acumen.article.web.rest.util.PaginationUtil;
import com.acumen.article.service.dto.ShippingAddressCriteria;
import com.acumen.article.service.ShippingAddressQueryService;
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
 * REST controller for managing ShippingAddress.
 */
@RestController
@RequestMapping("/api")
public class ShippingAddressResource {

    private final Logger log = LoggerFactory.getLogger(ShippingAddressResource.class);

    private static final String ENTITY_NAME = "shippingAddress";

    private final ShippingAddressService shippingAddressService;

    private final ShippingAddressQueryService shippingAddressQueryService;

    public ShippingAddressResource(ShippingAddressService shippingAddressService, ShippingAddressQueryService shippingAddressQueryService) {
        this.shippingAddressService = shippingAddressService;
        this.shippingAddressQueryService = shippingAddressQueryService;
    }

    /**
     * POST  /shipping-addresses : Create a new shippingAddress.
     *
     * @param shippingAddress the shippingAddress to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shippingAddress, or with status 400 (Bad Request) if the shippingAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipping-addresses")
    @Timed
    public ResponseEntity<ShippingAddress> createShippingAddress(@Valid @RequestBody ShippingAddress shippingAddress) throws URISyntaxException {
        log.debug("REST request to save ShippingAddress : {}", shippingAddress);
        if (shippingAddress.getId() != null) {
            throw new BadRequestAlertException("A new shippingAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShippingAddress result = shippingAddressService.save(shippingAddress);
        return ResponseEntity.created(new URI("/api/shipping-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipping-addresses : Updates an existing shippingAddress.
     *
     * @param shippingAddress the shippingAddress to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shippingAddress,
     * or with status 400 (Bad Request) if the shippingAddress is not valid,
     * or with status 500 (Internal Server Error) if the shippingAddress couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipping-addresses")
    @Timed
    public ResponseEntity<ShippingAddress> updateShippingAddress(@Valid @RequestBody ShippingAddress shippingAddress) throws URISyntaxException {
        log.debug("REST request to update ShippingAddress : {}", shippingAddress);
        if (shippingAddress.getId() == null) {
            return createShippingAddress(shippingAddress);
        }
        ShippingAddress result = shippingAddressService.save(shippingAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shippingAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipping-addresses : get all the shippingAddresses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of shippingAddresses in body
     */
    @GetMapping("/shipping-addresses")
    @Timed
    public ResponseEntity<List<ShippingAddress>> getAllShippingAddresses(ShippingAddressCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ShippingAddresses by criteria: {}", criteria);
        Page<ShippingAddress> page = shippingAddressQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipping-addresses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shipping-addresses/:id : get the "id" shippingAddress.
     *
     * @param id the id of the shippingAddress to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shippingAddress, or with status 404 (Not Found)
     */
    @GetMapping("/shipping-addresses/{id}")
    @Timed
    public ResponseEntity<ShippingAddress> getShippingAddress(@PathVariable Long id) {
        log.debug("REST request to get ShippingAddress : {}", id);
        ShippingAddress shippingAddress = shippingAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shippingAddress));
    }

    /**
     * DELETE  /shipping-addresses/:id : delete the "id" shippingAddress.
     *
     * @param id the id of the shippingAddress to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipping-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteShippingAddress(@PathVariable Long id) {
        log.debug("REST request to delete ShippingAddress : {}", id);
        shippingAddressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/shipping-addresses?query=:query : search for the shippingAddress corresponding
     * to the query.
     *
     * @param query the query of the shippingAddress search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/shipping-addresses")
    @Timed
    public ResponseEntity<List<ShippingAddress>> searchShippingAddresses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ShippingAddresses for query {}", query);
        Page<ShippingAddress> page = shippingAddressService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/shipping-addresses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
