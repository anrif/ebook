package com.acumen.article.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.acumen.article.domain.BillingAddress;
import com.acumen.article.service.BillingAddressService;
import com.acumen.article.web.rest.errors.BadRequestAlertException;
import com.acumen.article.web.rest.util.HeaderUtil;
import com.acumen.article.web.rest.util.PaginationUtil;
import com.acumen.article.service.dto.BillingAddressCriteria;
import com.acumen.article.service.BillingAddressQueryService;
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
 * REST controller for managing BillingAddress.
 */
@RestController
@RequestMapping("/api")
public class BillingAddressResource {

    private final Logger log = LoggerFactory.getLogger(BillingAddressResource.class);

    private static final String ENTITY_NAME = "billingAddress";

    private final BillingAddressService billingAddressService;

    private final BillingAddressQueryService billingAddressQueryService;

    public BillingAddressResource(BillingAddressService billingAddressService, BillingAddressQueryService billingAddressQueryService) {
        this.billingAddressService = billingAddressService;
        this.billingAddressQueryService = billingAddressQueryService;
    }

    /**
     * POST  /billing-addresses : Create a new billingAddress.
     *
     * @param billingAddress the billingAddress to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billingAddress, or with status 400 (Bad Request) if the billingAddress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/billing-addresses")
    @Timed
    public ResponseEntity<BillingAddress> createBillingAddress(@Valid @RequestBody BillingAddress billingAddress) throws URISyntaxException {
        log.debug("REST request to save BillingAddress : {}", billingAddress);
        if (billingAddress.getId() != null) {
            throw new BadRequestAlertException("A new billingAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillingAddress result = billingAddressService.save(billingAddress);
        return ResponseEntity.created(new URI("/api/billing-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /billing-addresses : Updates an existing billingAddress.
     *
     * @param billingAddress the billingAddress to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billingAddress,
     * or with status 400 (Bad Request) if the billingAddress is not valid,
     * or with status 500 (Internal Server Error) if the billingAddress couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/billing-addresses")
    @Timed
    public ResponseEntity<BillingAddress> updateBillingAddress(@Valid @RequestBody BillingAddress billingAddress) throws URISyntaxException {
        log.debug("REST request to update BillingAddress : {}", billingAddress);
        if (billingAddress.getId() == null) {
            return createBillingAddress(billingAddress);
        }
        BillingAddress result = billingAddressService.save(billingAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, billingAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /billing-addresses : get all the billingAddresses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of billingAddresses in body
     */
    @GetMapping("/billing-addresses")
    @Timed
    public ResponseEntity<List<BillingAddress>> getAllBillingAddresses(BillingAddressCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BillingAddresses by criteria: {}", criteria);
        Page<BillingAddress> page = billingAddressQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/billing-addresses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /billing-addresses/:id : get the "id" billingAddress.
     *
     * @param id the id of the billingAddress to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billingAddress, or with status 404 (Not Found)
     */
    @GetMapping("/billing-addresses/{id}")
    @Timed
    public ResponseEntity<BillingAddress> getBillingAddress(@PathVariable Long id) {
        log.debug("REST request to get BillingAddress : {}", id);
        BillingAddress billingAddress = billingAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(billingAddress));
    }

    /**
     * DELETE  /billing-addresses/:id : delete the "id" billingAddress.
     *
     * @param id the id of the billingAddress to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/billing-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteBillingAddress(@PathVariable Long id) {
        log.debug("REST request to delete BillingAddress : {}", id);
        billingAddressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/billing-addresses?query=:query : search for the billingAddress corresponding
     * to the query.
     *
     * @param query the query of the billingAddress search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/billing-addresses")
    @Timed
    public ResponseEntity<List<BillingAddress>> searchBillingAddresses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BillingAddresses for query {}", query);
        Page<BillingAddress> page = billingAddressService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/billing-addresses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
