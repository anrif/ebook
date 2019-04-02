package com.acumen.article.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.acumen.article.domain.ShoppingCart;
import com.acumen.article.service.ShoppingCartService;
import com.acumen.article.web.rest.errors.BadRequestAlertException;
import com.acumen.article.web.rest.util.HeaderUtil;
import com.acumen.article.web.rest.util.PaginationUtil;
import com.acumen.article.service.dto.ShoppingCartCriteria;
import com.acumen.article.service.ShoppingCartQueryService;
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
 * REST controller for managing ShoppingCart.
 */
@RestController
@RequestMapping("/api")
public class ShoppingCartResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartResource.class);

    private static final String ENTITY_NAME = "shoppingCart";

    private final ShoppingCartService shoppingCartService;

    private final ShoppingCartQueryService shoppingCartQueryService;

    public ShoppingCartResource(ShoppingCartService shoppingCartService, ShoppingCartQueryService shoppingCartQueryService) {
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartQueryService = shoppingCartQueryService;
    }

    /**
     * POST  /shopping-carts : Create a new shoppingCart.
     *
     * @param shoppingCart the shoppingCart to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shoppingCart, or with status 400 (Bad Request) if the shoppingCart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shopping-carts")
    @Timed
    public ResponseEntity<ShoppingCart> createShoppingCart(@Valid @RequestBody ShoppingCart shoppingCart) throws URISyntaxException {
        log.debug("REST request to save ShoppingCart : {}", shoppingCart);
        if (shoppingCart.getId() != null) {
            throw new BadRequestAlertException("A new shoppingCart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoppingCart result = shoppingCartService.save(shoppingCart);
        return ResponseEntity.created(new URI("/api/shopping-carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shopping-carts : Updates an existing shoppingCart.
     *
     * @param shoppingCart the shoppingCart to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shoppingCart,
     * or with status 400 (Bad Request) if the shoppingCart is not valid,
     * or with status 500 (Internal Server Error) if the shoppingCart couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shopping-carts")
    @Timed
    public ResponseEntity<ShoppingCart> updateShoppingCart(@Valid @RequestBody ShoppingCart shoppingCart) throws URISyntaxException {
        log.debug("REST request to update ShoppingCart : {}", shoppingCart);
        if (shoppingCart.getId() == null) {
            return createShoppingCart(shoppingCart);
        }
        ShoppingCart result = shoppingCartService.save(shoppingCart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shoppingCart.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shopping-carts : get all the shoppingCarts.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of shoppingCarts in body
     */
    @GetMapping("/shopping-carts")
    @Timed
    public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts(ShoppingCartCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ShoppingCarts by criteria: {}", criteria);
        Page<ShoppingCart> page = shoppingCartQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shopping-carts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shopping-carts/:id : get the "id" shoppingCart.
     *
     * @param id the id of the shoppingCart to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shoppingCart, or with status 404 (Not Found)
     */
    @GetMapping("/shopping-carts/{id}")
    @Timed
    public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable Long id) {
        log.debug("REST request to get ShoppingCart : {}", id);
        ShoppingCart shoppingCart = shoppingCartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shoppingCart));
    }

    /**
     * DELETE  /shopping-carts/:id : delete the "id" shoppingCart.
     *
     * @param id the id of the shoppingCart to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shopping-carts/{id}")
    @Timed
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable Long id) {
        log.debug("REST request to delete ShoppingCart : {}", id);
        shoppingCartService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/shopping-carts?query=:query : search for the shoppingCart corresponding
     * to the query.
     *
     * @param query the query of the shoppingCart search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/shopping-carts")
    @Timed
    public ResponseEntity<List<ShoppingCart>> searchShoppingCarts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ShoppingCarts for query {}", query);
        Page<ShoppingCart> page = shoppingCartService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/shopping-carts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
