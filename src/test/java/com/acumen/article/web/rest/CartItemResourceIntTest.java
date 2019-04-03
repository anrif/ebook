package com.acumen.article.web.rest;

import com.acumen.article.ArticleApp;

import com.acumen.article.domain.CartItem;
import com.acumen.article.domain.Book;
import com.acumen.article.domain.ShoppingCart;
import com.acumen.article.domain.Ordered;
import com.acumen.article.repository.CartItemRepository;
import com.acumen.article.service.CartItemService;
import com.acumen.article.repository.search.CartItemSearchRepository;
import com.acumen.article.web.rest.errors.ExceptionTranslator;
import com.acumen.article.service.dto.CartItemCriteria;
import com.acumen.article.service.CartItemQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.acumen.article.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CartItemResource REST controller.
 *
 * @see CartItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleApp.class)
public class CartItemResourceIntTest {

    private static final Integer DEFAULT_QTY = 1;
    private static final Integer UPDATED_QTY = 2;

    private static final BigDecimal DEFAULT_SUBTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBTOTAL = new BigDecimal(2);

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartItemSearchRepository cartItemSearchRepository;

    @Autowired
    private CartItemQueryService cartItemQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCartItemMockMvc;

    private CartItem cartItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CartItemResource cartItemResource = new CartItemResource(cartItemService, cartItemQueryService);
        this.restCartItemMockMvc = MockMvcBuilders.standaloneSetup(cartItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartItem createEntity(EntityManager em) {
        CartItem cartItem = new CartItem()
            .qty(DEFAULT_QTY)
            .subtotal(DEFAULT_SUBTOTAL);
        return cartItem;
    }

    @Before
    public void initTest() {
        cartItemSearchRepository.deleteAll();
        cartItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCartItem() throws Exception {
        int databaseSizeBeforeCreate = cartItemRepository.findAll().size();

        // Create the CartItem
        restCartItemMockMvc.perform(post("/api/cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartItem)))
            .andExpect(status().isCreated());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeCreate + 1);
        CartItem testCartItem = cartItemList.get(cartItemList.size() - 1);
        assertThat(testCartItem.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testCartItem.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);

        // Validate the CartItem in Elasticsearch
        CartItem cartItemEs = cartItemSearchRepository.findOne(testCartItem.getId());
        assertThat(cartItemEs).isEqualToIgnoringGivenFields(testCartItem);
    }

    @Test
    @Transactional
    public void createCartItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cartItemRepository.findAll().size();

        // Create the CartItem with an existing ID
        cartItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartItemMockMvc.perform(post("/api/cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartItem)))
            .andExpect(status().isBadRequest());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCartItems() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        // Get all the cartItemList
        restCartItemMockMvc.perform(get("/api/cart-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getCartItem() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        // Get the cartItem
        restCartItemMockMvc.perform(get("/api/cart-items/{id}", cartItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cartItem.getId().intValue()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getAllCartItemsByQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        // Get all the cartItemList where qty equals to DEFAULT_QTY
        defaultCartItemShouldBeFound("qty.equals=" + DEFAULT_QTY);

        // Get all the cartItemList where qty equals to UPDATED_QTY
        defaultCartItemShouldNotBeFound("qty.equals=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    public void getAllCartItemsByQtyIsInShouldWork() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        // Get all the cartItemList where qty in DEFAULT_QTY or UPDATED_QTY
        defaultCartItemShouldBeFound("qty.in=" + DEFAULT_QTY + "," + UPDATED_QTY);

        // Get all the cartItemList where qty equals to UPDATED_QTY
        defaultCartItemShouldNotBeFound("qty.in=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    public void getAllCartItemsByQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        // Get all the cartItemList where qty is not null
        defaultCartItemShouldBeFound("qty.specified=true");

        // Get all the cartItemList where qty is null
        defaultCartItemShouldNotBeFound("qty.specified=false");
    }

    @Test
    @Transactional
    public void getAllCartItemsByQtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        // Get all the cartItemList where qty greater than or equals to DEFAULT_QTY
        defaultCartItemShouldBeFound("qty.greaterOrEqualThan=" + DEFAULT_QTY);

        // Get all the cartItemList where qty greater than or equals to UPDATED_QTY
        defaultCartItemShouldNotBeFound("qty.greaterOrEqualThan=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    public void getAllCartItemsByQtyIsLessThanSomething() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        // Get all the cartItemList where qty less than or equals to DEFAULT_QTY
        defaultCartItemShouldNotBeFound("qty.lessThan=" + DEFAULT_QTY);

        // Get all the cartItemList where qty less than or equals to UPDATED_QTY
        defaultCartItemShouldBeFound("qty.lessThan=" + UPDATED_QTY);
    }


    @Test
    @Transactional
    public void getAllCartItemsBySubtotalIsEqualToSomething() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        // Get all the cartItemList where subtotal equals to DEFAULT_SUBTOTAL
        defaultCartItemShouldBeFound("subtotal.equals=" + DEFAULT_SUBTOTAL);

        // Get all the cartItemList where subtotal equals to UPDATED_SUBTOTAL
        defaultCartItemShouldNotBeFound("subtotal.equals=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    public void getAllCartItemsBySubtotalIsInShouldWork() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        // Get all the cartItemList where subtotal in DEFAULT_SUBTOTAL or UPDATED_SUBTOTAL
        defaultCartItemShouldBeFound("subtotal.in=" + DEFAULT_SUBTOTAL + "," + UPDATED_SUBTOTAL);

        // Get all the cartItemList where subtotal equals to UPDATED_SUBTOTAL
        defaultCartItemShouldNotBeFound("subtotal.in=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    public void getAllCartItemsBySubtotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartItemRepository.saveAndFlush(cartItem);

        // Get all the cartItemList where subtotal is not null
        defaultCartItemShouldBeFound("subtotal.specified=true");

        // Get all the cartItemList where subtotal is null
        defaultCartItemShouldNotBeFound("subtotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllCartItemsByBookIsEqualToSomething() throws Exception {
        // Initialize the database
        Book book = BookResourceIntTest.createEntity(em);
        em.persist(book);
        em.flush();
        cartItem.setBook(book);
        cartItemRepository.saveAndFlush(cartItem);
        Long bookId = book.getId();

        // Get all the cartItemList where book equals to bookId
        defaultCartItemShouldBeFound("bookId.equals=" + bookId);

        // Get all the cartItemList where book equals to bookId + 1
        defaultCartItemShouldNotBeFound("bookId.equals=" + (bookId + 1));
    }


    @Test
    @Transactional
    public void getAllCartItemsByShoppingCartIsEqualToSomething() throws Exception {
        // Initialize the database
        ShoppingCart shoppingCart = ShoppingCartResourceIntTest.createEntity(em);
        em.persist(shoppingCart);
        em.flush();
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.saveAndFlush(cartItem);
        Long shoppingCartId = shoppingCart.getId();

        // Get all the cartItemList where shoppingCart equals to shoppingCartId
        defaultCartItemShouldBeFound("shoppingCartId.equals=" + shoppingCartId);

        // Get all the cartItemList where shoppingCart equals to shoppingCartId + 1
        defaultCartItemShouldNotBeFound("shoppingCartId.equals=" + (shoppingCartId + 1));
    }


    @Test
    @Transactional
    public void getAllCartItemsByOrderedIsEqualToSomething() throws Exception {
        // Initialize the database
        Ordered ordered = OrderedResourceIntTest.createEntity(em);
        em.persist(ordered);
        em.flush();
        cartItem.setOrdered(ordered);
        cartItemRepository.saveAndFlush(cartItem);
        Long orderedId = ordered.getId();

        // Get all the cartItemList where ordered equals to orderedId
        defaultCartItemShouldBeFound("orderedId.equals=" + orderedId);

        // Get all the cartItemList where ordered equals to orderedId + 1
        defaultCartItemShouldNotBeFound("orderedId.equals=" + (orderedId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCartItemShouldBeFound(String filter) throws Exception {
        restCartItemMockMvc.perform(get("/api/cart-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCartItemShouldNotBeFound(String filter) throws Exception {
        restCartItemMockMvc.perform(get("/api/cart-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCartItem() throws Exception {
        // Get the cartItem
        restCartItemMockMvc.perform(get("/api/cart-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCartItem() throws Exception {
        // Initialize the database
        cartItemService.save(cartItem);

        int databaseSizeBeforeUpdate = cartItemRepository.findAll().size();

        // Update the cartItem
        CartItem updatedCartItem = cartItemRepository.findOne(cartItem.getId());
        // Disconnect from session so that the updates on updatedCartItem are not directly saved in db
        em.detach(updatedCartItem);
        updatedCartItem
            .qty(UPDATED_QTY)
            .subtotal(UPDATED_SUBTOTAL);

        restCartItemMockMvc.perform(put("/api/cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCartItem)))
            .andExpect(status().isOk());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeUpdate);
        CartItem testCartItem = cartItemList.get(cartItemList.size() - 1);
        assertThat(testCartItem.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testCartItem.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);

        // Validate the CartItem in Elasticsearch
        CartItem cartItemEs = cartItemSearchRepository.findOne(testCartItem.getId());
        assertThat(cartItemEs).isEqualToIgnoringGivenFields(testCartItem);
    }

    @Test
    @Transactional
    public void updateNonExistingCartItem() throws Exception {
        int databaseSizeBeforeUpdate = cartItemRepository.findAll().size();

        // Create the CartItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCartItemMockMvc.perform(put("/api/cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartItem)))
            .andExpect(status().isCreated());

        // Validate the CartItem in the database
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCartItem() throws Exception {
        // Initialize the database
        cartItemService.save(cartItem);

        int databaseSizeBeforeDelete = cartItemRepository.findAll().size();

        // Get the cartItem
        restCartItemMockMvc.perform(delete("/api/cart-items/{id}", cartItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean cartItemExistsInEs = cartItemSearchRepository.exists(cartItem.getId());
        assertThat(cartItemExistsInEs).isFalse();

        // Validate the database is empty
        List<CartItem> cartItemList = cartItemRepository.findAll();
        assertThat(cartItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCartItem() throws Exception {
        // Initialize the database
        cartItemService.save(cartItem);

        // Search the cartItem
        restCartItemMockMvc.perform(get("/api/_search/cart-items?query=id:" + cartItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartItem.class);
        CartItem cartItem1 = new CartItem();
        cartItem1.setId(1L);
        CartItem cartItem2 = new CartItem();
        cartItem2.setId(cartItem1.getId());
        assertThat(cartItem1).isEqualTo(cartItem2);
        cartItem2.setId(2L);
        assertThat(cartItem1).isNotEqualTo(cartItem2);
        cartItem1.setId(null);
        assertThat(cartItem1).isNotEqualTo(cartItem2);
    }
}
