package com.acumen.article.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.acumen.article.domain.CartItem;
import com.acumen.article.service.CartItemService;
import com.acumen.article.web.rest.errors.BadRequestAlertException;
import com.acumen.article.web.rest.util.HeaderUtil;
import com.acumen.article.web.rest.util.PaginationUtil;
import com.acumen.article.service.dto.CartItemCriteria;
import com.acumen.article.service.CartItemQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CartItem.
 */
@RestController
@RequestMapping("/api")
public class CartItemResource {

    private final Logger log = LoggerFactory.getLogger(CartItemResource.class);

    private static final String ENTITY_NAME = "cartItem";

    private final CartItemService cartItemService;

    private final CartItemQueryService cartItemQueryService;

    public CartItemResource(CartItemService cartItemService, CartItemQueryService cartItemQueryService) {
        this.cartItemService = cartItemService;
        this.cartItemQueryService = cartItemQueryService;
    }

    /**
     * POST  /cart-items : Create a new cartItem.
     *
     * @param cartItem the cartItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cartItem, or with status 400 (Bad Request) if the cartItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cart-items")
    @Timed
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem) throws URISyntaxException {
        log.debug("REST request to save CartItem : {}", cartItem);
        if (cartItem.getId() != null) {
            throw new BadRequestAlertException("A new cartItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CartItem result = cartItemService.save(cartItem);
        return ResponseEntity.created(new URI("/api/cart-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cart-items : Updates an existing cartItem.
     *
     * @param cartItem the cartItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cartItem,
     * or with status 400 (Bad Request) if the cartItem is not valid,
     * or with status 500 (Internal Server Error) if the cartItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cart-items")
    @Timed
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem) throws URISyntaxException {
        log.debug("REST request to update CartItem : {}", cartItem);
        if (cartItem.getId() == null) {
            return createCartItem(cartItem);
        }
        CartItem result = cartItemService.save(cartItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cartItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cart-items : get all the cartItems.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of cartItems in body
     */
    @GetMapping("/cart-items")
    @Timed
    public ResponseEntity<List<CartItem>> getAllCartItems(CartItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CartItems by criteria: {}", criteria);
        Page<CartItem> page = cartItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cart-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cart-items/:id : get the "id" cartItem.
     *
     * @param id the id of the cartItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cartItem, or with status 404 (Not Found)
     */
    @GetMapping("/cart-items/{id}")
    @Timed
    public ResponseEntity<CartItem> getCartItem(@PathVariable Long id) {
        log.debug("REST request to get CartItem : {}", id);
        CartItem cartItem = cartItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cartItem));
    }

    /**
     * DELETE  /cart-items/:id : delete the "id" cartItem.
     *
     * @param id the id of the cartItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cart-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        log.debug("REST request to delete CartItem : {}", id);
        cartItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cart-items?query=:query : search for the cartItem corresponding
     * to the query.
     *
     * @param query the query of the cartItem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cart-items")
    @Timed
    public ResponseEntity<List<CartItem>> searchCartItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CartItems for query {}", query);
        Page<CartItem> page = cartItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cart-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
