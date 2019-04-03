package com.acumen.article.web.rest;

import com.acumen.article.ArticleApp;

import com.acumen.article.domain.Ordered;
import com.acumen.article.domain.ShippingAddress;
import com.acumen.article.domain.BillingAddress;
import com.acumen.article.domain.Payment;
import com.acumen.article.repository.OrderedRepository;
import com.acumen.article.service.OrderedService;
import com.acumen.article.repository.search.OrderedSearchRepository;
import com.acumen.article.web.rest.errors.ExceptionTranslator;
import com.acumen.article.service.dto.OrderedCriteria;
import com.acumen.article.service.OrderedQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.acumen.article.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrderedResource REST controller.
 *
 * @see OrderedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleApp.class)
public class OrderedResourceIntTest {

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SHIPPING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SHIPPING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SHIPPING_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_STATUS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ORDER_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_ORDER_TOTAL = new BigDecimal(2);

    @Autowired
    private OrderedRepository orderedRepository;

    @Autowired
    private OrderedService orderedService;

    @Autowired
    private OrderedSearchRepository orderedSearchRepository;

    @Autowired
    private OrderedQueryService orderedQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderedMockMvc;

    private Ordered ordered;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderedResource orderedResource = new OrderedResource(orderedService, orderedQueryService);
        this.restOrderedMockMvc = MockMvcBuilders.standaloneSetup(orderedResource)
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
    public static Ordered createEntity(EntityManager em) {
        Ordered ordered = new Ordered()
            .orderDate(DEFAULT_ORDER_DATE)
            .shippingDate(DEFAULT_SHIPPING_DATE)
            .shippingMethod(DEFAULT_SHIPPING_METHOD)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .orderTotal(DEFAULT_ORDER_TOTAL);
        return ordered;
    }

    @Before
    public void initTest() {
        orderedSearchRepository.deleteAll();
        ordered = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdered() throws Exception {
        int databaseSizeBeforeCreate = orderedRepository.findAll().size();

        // Create the Ordered
        restOrderedMockMvc.perform(post("/api/ordereds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordered)))
            .andExpect(status().isCreated());

        // Validate the Ordered in the database
        List<Ordered> orderedList = orderedRepository.findAll();
        assertThat(orderedList).hasSize(databaseSizeBeforeCreate + 1);
        Ordered testOrdered = orderedList.get(orderedList.size() - 1);
        assertThat(testOrdered.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testOrdered.getShippingDate()).isEqualTo(DEFAULT_SHIPPING_DATE);
        assertThat(testOrdered.getShippingMethod()).isEqualTo(DEFAULT_SHIPPING_METHOD);
        assertThat(testOrdered.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testOrdered.getOrderTotal()).isEqualTo(DEFAULT_ORDER_TOTAL);

        // Validate the Ordered in Elasticsearch
        Ordered orderedEs = orderedSearchRepository.findOne(testOrdered.getId());
        assertThat(orderedEs).isEqualToIgnoringGivenFields(testOrdered);
    }

    @Test
    @Transactional
    public void createOrderedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderedRepository.findAll().size();

        // Create the Ordered with an existing ID
        ordered.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderedMockMvc.perform(post("/api/ordereds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordered)))
            .andExpect(status().isBadRequest());

        // Validate the Ordered in the database
        List<Ordered> orderedList = orderedRepository.findAll();
        assertThat(orderedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrderDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderedRepository.findAll().size();
        // set the field null
        ordered.setOrderDate(null);

        // Create the Ordered, which fails.

        restOrderedMockMvc.perform(post("/api/ordereds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordered)))
            .andExpect(status().isBadRequest());

        List<Ordered> orderedList = orderedRepository.findAll();
        assertThat(orderedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShippingDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderedRepository.findAll().size();
        // set the field null
        ordered.setShippingDate(null);

        // Create the Ordered, which fails.

        restOrderedMockMvc.perform(post("/api/ordereds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordered)))
            .andExpect(status().isBadRequest());

        List<Ordered> orderedList = orderedRepository.findAll();
        assertThat(orderedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShippingMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderedRepository.findAll().size();
        // set the field null
        ordered.setShippingMethod(null);

        // Create the Ordered, which fails.

        restOrderedMockMvc.perform(post("/api/ordereds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordered)))
            .andExpect(status().isBadRequest());

        List<Ordered> orderedList = orderedRepository.findAll();
        assertThat(orderedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderedRepository.findAll().size();
        // set the field null
        ordered.setOrderStatus(null);

        // Create the Ordered, which fails.

        restOrderedMockMvc.perform(post("/api/ordereds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordered)))
            .andExpect(status().isBadRequest());

        List<Ordered> orderedList = orderedRepository.findAll();
        assertThat(orderedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderedRepository.findAll().size();
        // set the field null
        ordered.setOrderTotal(null);

        // Create the Ordered, which fails.

        restOrderedMockMvc.perform(post("/api/ordereds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordered)))
            .andExpect(status().isBadRequest());

        List<Ordered> orderedList = orderedRepository.findAll();
        assertThat(orderedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrdereds() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList
        restOrderedMockMvc.perform(get("/api/ordereds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordered.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].shippingDate").value(hasItem(DEFAULT_SHIPPING_DATE.toString())))
            .andExpect(jsonPath("$.[*].shippingMethod").value(hasItem(DEFAULT_SHIPPING_METHOD.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].orderTotal").value(hasItem(DEFAULT_ORDER_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getOrdered() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get the ordered
        restOrderedMockMvc.perform(get("/api/ordereds/{id}", ordered.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ordered.getId().intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.shippingDate").value(DEFAULT_SHIPPING_DATE.toString()))
            .andExpect(jsonPath("$.shippingMethod").value(DEFAULT_SHIPPING_METHOD.toString()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.orderTotal").value(DEFAULT_ORDER_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getAllOrderedsByOrderDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where orderDate equals to DEFAULT_ORDER_DATE
        defaultOrderedShouldBeFound("orderDate.equals=" + DEFAULT_ORDER_DATE);

        // Get all the orderedList where orderDate equals to UPDATED_ORDER_DATE
        defaultOrderedShouldNotBeFound("orderDate.equals=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderedsByOrderDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where orderDate in DEFAULT_ORDER_DATE or UPDATED_ORDER_DATE
        defaultOrderedShouldBeFound("orderDate.in=" + DEFAULT_ORDER_DATE + "," + UPDATED_ORDER_DATE);

        // Get all the orderedList where orderDate equals to UPDATED_ORDER_DATE
        defaultOrderedShouldNotBeFound("orderDate.in=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderedsByOrderDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where orderDate is not null
        defaultOrderedShouldBeFound("orderDate.specified=true");

        // Get all the orderedList where orderDate is null
        defaultOrderedShouldNotBeFound("orderDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderedsByOrderDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where orderDate greater than or equals to DEFAULT_ORDER_DATE
        defaultOrderedShouldBeFound("orderDate.greaterOrEqualThan=" + DEFAULT_ORDER_DATE);

        // Get all the orderedList where orderDate greater than or equals to UPDATED_ORDER_DATE
        defaultOrderedShouldNotBeFound("orderDate.greaterOrEqualThan=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderedsByOrderDateIsLessThanSomething() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where orderDate less than or equals to DEFAULT_ORDER_DATE
        defaultOrderedShouldNotBeFound("orderDate.lessThan=" + DEFAULT_ORDER_DATE);

        // Get all the orderedList where orderDate less than or equals to UPDATED_ORDER_DATE
        defaultOrderedShouldBeFound("orderDate.lessThan=" + UPDATED_ORDER_DATE);
    }


    @Test
    @Transactional
    public void getAllOrderedsByShippingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where shippingDate equals to DEFAULT_SHIPPING_DATE
        defaultOrderedShouldBeFound("shippingDate.equals=" + DEFAULT_SHIPPING_DATE);

        // Get all the orderedList where shippingDate equals to UPDATED_SHIPPING_DATE
        defaultOrderedShouldNotBeFound("shippingDate.equals=" + UPDATED_SHIPPING_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderedsByShippingDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where shippingDate in DEFAULT_SHIPPING_DATE or UPDATED_SHIPPING_DATE
        defaultOrderedShouldBeFound("shippingDate.in=" + DEFAULT_SHIPPING_DATE + "," + UPDATED_SHIPPING_DATE);

        // Get all the orderedList where shippingDate equals to UPDATED_SHIPPING_DATE
        defaultOrderedShouldNotBeFound("shippingDate.in=" + UPDATED_SHIPPING_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderedsByShippingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where shippingDate is not null
        defaultOrderedShouldBeFound("shippingDate.specified=true");

        // Get all the orderedList where shippingDate is null
        defaultOrderedShouldNotBeFound("shippingDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderedsByShippingDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where shippingDate greater than or equals to DEFAULT_SHIPPING_DATE
        defaultOrderedShouldBeFound("shippingDate.greaterOrEqualThan=" + DEFAULT_SHIPPING_DATE);

        // Get all the orderedList where shippingDate greater than or equals to UPDATED_SHIPPING_DATE
        defaultOrderedShouldNotBeFound("shippingDate.greaterOrEqualThan=" + UPDATED_SHIPPING_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderedsByShippingDateIsLessThanSomething() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where shippingDate less than or equals to DEFAULT_SHIPPING_DATE
        defaultOrderedShouldNotBeFound("shippingDate.lessThan=" + DEFAULT_SHIPPING_DATE);

        // Get all the orderedList where shippingDate less than or equals to UPDATED_SHIPPING_DATE
        defaultOrderedShouldBeFound("shippingDate.lessThan=" + UPDATED_SHIPPING_DATE);
    }


    @Test
    @Transactional
    public void getAllOrderedsByShippingMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where shippingMethod equals to DEFAULT_SHIPPING_METHOD
        defaultOrderedShouldBeFound("shippingMethod.equals=" + DEFAULT_SHIPPING_METHOD);

        // Get all the orderedList where shippingMethod equals to UPDATED_SHIPPING_METHOD
        defaultOrderedShouldNotBeFound("shippingMethod.equals=" + UPDATED_SHIPPING_METHOD);
    }

    @Test
    @Transactional
    public void getAllOrderedsByShippingMethodIsInShouldWork() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where shippingMethod in DEFAULT_SHIPPING_METHOD or UPDATED_SHIPPING_METHOD
        defaultOrderedShouldBeFound("shippingMethod.in=" + DEFAULT_SHIPPING_METHOD + "," + UPDATED_SHIPPING_METHOD);

        // Get all the orderedList where shippingMethod equals to UPDATED_SHIPPING_METHOD
        defaultOrderedShouldNotBeFound("shippingMethod.in=" + UPDATED_SHIPPING_METHOD);
    }

    @Test
    @Transactional
    public void getAllOrderedsByShippingMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where shippingMethod is not null
        defaultOrderedShouldBeFound("shippingMethod.specified=true");

        // Get all the orderedList where shippingMethod is null
        defaultOrderedShouldNotBeFound("shippingMethod.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderedsByOrderStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where orderStatus equals to DEFAULT_ORDER_STATUS
        defaultOrderedShouldBeFound("orderStatus.equals=" + DEFAULT_ORDER_STATUS);

        // Get all the orderedList where orderStatus equals to UPDATED_ORDER_STATUS
        defaultOrderedShouldNotBeFound("orderStatus.equals=" + UPDATED_ORDER_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrderedsByOrderStatusIsInShouldWork() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where orderStatus in DEFAULT_ORDER_STATUS or UPDATED_ORDER_STATUS
        defaultOrderedShouldBeFound("orderStatus.in=" + DEFAULT_ORDER_STATUS + "," + UPDATED_ORDER_STATUS);

        // Get all the orderedList where orderStatus equals to UPDATED_ORDER_STATUS
        defaultOrderedShouldNotBeFound("orderStatus.in=" + UPDATED_ORDER_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrderedsByOrderStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where orderStatus is not null
        defaultOrderedShouldBeFound("orderStatus.specified=true");

        // Get all the orderedList where orderStatus is null
        defaultOrderedShouldNotBeFound("orderStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderedsByOrderTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where orderTotal equals to DEFAULT_ORDER_TOTAL
        defaultOrderedShouldBeFound("orderTotal.equals=" + DEFAULT_ORDER_TOTAL);

        // Get all the orderedList where orderTotal equals to UPDATED_ORDER_TOTAL
        defaultOrderedShouldNotBeFound("orderTotal.equals=" + UPDATED_ORDER_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderedsByOrderTotalIsInShouldWork() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where orderTotal in DEFAULT_ORDER_TOTAL or UPDATED_ORDER_TOTAL
        defaultOrderedShouldBeFound("orderTotal.in=" + DEFAULT_ORDER_TOTAL + "," + UPDATED_ORDER_TOTAL);

        // Get all the orderedList where orderTotal equals to UPDATED_ORDER_TOTAL
        defaultOrderedShouldNotBeFound("orderTotal.in=" + UPDATED_ORDER_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderedsByOrderTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderedRepository.saveAndFlush(ordered);

        // Get all the orderedList where orderTotal is not null
        defaultOrderedShouldBeFound("orderTotal.specified=true");

        // Get all the orderedList where orderTotal is null
        defaultOrderedShouldNotBeFound("orderTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderedsByShippingAddresssIsEqualToSomething() throws Exception {
        // Initialize the database
        ShippingAddress shippingAddresss = ShippingAddressResourceIntTest.createEntity(em);
        em.persist(shippingAddresss);
        em.flush();
        ordered.addShippingAddresss(shippingAddresss);
        orderedRepository.saveAndFlush(ordered);
        Long shippingAddresssId = shippingAddresss.getId();

        // Get all the orderedList where shippingAddresss equals to shippingAddresssId
        defaultOrderedShouldBeFound("shippingAddresssId.equals=" + shippingAddresssId);

        // Get all the orderedList where shippingAddresss equals to shippingAddresssId + 1
        defaultOrderedShouldNotBeFound("shippingAddresssId.equals=" + (shippingAddresssId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderedsByBillingAddresssIsEqualToSomething() throws Exception {
        // Initialize the database
        BillingAddress billingAddresss = BillingAddressResourceIntTest.createEntity(em);
        em.persist(billingAddresss);
        em.flush();
        ordered.addBillingAddresss(billingAddresss);
        orderedRepository.saveAndFlush(ordered);
        Long billingAddresssId = billingAddresss.getId();

        // Get all the orderedList where billingAddresss equals to billingAddresssId
        defaultOrderedShouldBeFound("billingAddresssId.equals=" + billingAddresssId);

        // Get all the orderedList where billingAddresss equals to billingAddresssId + 1
        defaultOrderedShouldNotBeFound("billingAddresssId.equals=" + (billingAddresssId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderedsByPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        Payment payment = PaymentResourceIntTest.createEntity(em);
        em.persist(payment);
        em.flush();
        ordered.addPayment(payment);
        orderedRepository.saveAndFlush(ordered);
        Long paymentId = payment.getId();

        // Get all the orderedList where payment equals to paymentId
        defaultOrderedShouldBeFound("paymentId.equals=" + paymentId);

        // Get all the orderedList where payment equals to paymentId + 1
        defaultOrderedShouldNotBeFound("paymentId.equals=" + (paymentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultOrderedShouldBeFound(String filter) throws Exception {
        restOrderedMockMvc.perform(get("/api/ordereds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordered.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].shippingDate").value(hasItem(DEFAULT_SHIPPING_DATE.toString())))
            .andExpect(jsonPath("$.[*].shippingMethod").value(hasItem(DEFAULT_SHIPPING_METHOD.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].orderTotal").value(hasItem(DEFAULT_ORDER_TOTAL.intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultOrderedShouldNotBeFound(String filter) throws Exception {
        restOrderedMockMvc.perform(get("/api/ordereds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingOrdered() throws Exception {
        // Get the ordered
        restOrderedMockMvc.perform(get("/api/ordereds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdered() throws Exception {
        // Initialize the database
        orderedService.save(ordered);

        int databaseSizeBeforeUpdate = orderedRepository.findAll().size();

        // Update the ordered
        Ordered updatedOrdered = orderedRepository.findOne(ordered.getId());
        // Disconnect from session so that the updates on updatedOrdered are not directly saved in db
        em.detach(updatedOrdered);
        updatedOrdered
            .orderDate(UPDATED_ORDER_DATE)
            .shippingDate(UPDATED_SHIPPING_DATE)
            .shippingMethod(UPDATED_SHIPPING_METHOD)
            .orderStatus(UPDATED_ORDER_STATUS)
            .orderTotal(UPDATED_ORDER_TOTAL);

        restOrderedMockMvc.perform(put("/api/ordereds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrdered)))
            .andExpect(status().isOk());

        // Validate the Ordered in the database
        List<Ordered> orderedList = orderedRepository.findAll();
        assertThat(orderedList).hasSize(databaseSizeBeforeUpdate);
        Ordered testOrdered = orderedList.get(orderedList.size() - 1);
        assertThat(testOrdered.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testOrdered.getShippingDate()).isEqualTo(UPDATED_SHIPPING_DATE);
        assertThat(testOrdered.getShippingMethod()).isEqualTo(UPDATED_SHIPPING_METHOD);
        assertThat(testOrdered.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testOrdered.getOrderTotal()).isEqualTo(UPDATED_ORDER_TOTAL);

        // Validate the Ordered in Elasticsearch
        Ordered orderedEs = orderedSearchRepository.findOne(testOrdered.getId());
        assertThat(orderedEs).isEqualToIgnoringGivenFields(testOrdered);
    }

    @Test
    @Transactional
    public void updateNonExistingOrdered() throws Exception {
        int databaseSizeBeforeUpdate = orderedRepository.findAll().size();

        // Create the Ordered

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderedMockMvc.perform(put("/api/ordereds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordered)))
            .andExpect(status().isCreated());

        // Validate the Ordered in the database
        List<Ordered> orderedList = orderedRepository.findAll();
        assertThat(orderedList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrdered() throws Exception {
        // Initialize the database
        orderedService.save(ordered);

        int databaseSizeBeforeDelete = orderedRepository.findAll().size();

        // Get the ordered
        restOrderedMockMvc.perform(delete("/api/ordereds/{id}", ordered.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean orderedExistsInEs = orderedSearchRepository.exists(ordered.getId());
        assertThat(orderedExistsInEs).isFalse();

        // Validate the database is empty
        List<Ordered> orderedList = orderedRepository.findAll();
        assertThat(orderedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOrdered() throws Exception {
        // Initialize the database
        orderedService.save(ordered);

        // Search the ordered
        restOrderedMockMvc.perform(get("/api/_search/ordereds?query=id:" + ordered.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordered.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].shippingDate").value(hasItem(DEFAULT_SHIPPING_DATE.toString())))
            .andExpect(jsonPath("$.[*].shippingMethod").value(hasItem(DEFAULT_SHIPPING_METHOD.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].orderTotal").value(hasItem(DEFAULT_ORDER_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ordered.class);
        Ordered ordered1 = new Ordered();
        ordered1.setId(1L);
        Ordered ordered2 = new Ordered();
        ordered2.setId(ordered1.getId());
        assertThat(ordered1).isEqualTo(ordered2);
        ordered2.setId(2L);
        assertThat(ordered1).isNotEqualTo(ordered2);
        ordered1.setId(null);
        assertThat(ordered1).isNotEqualTo(ordered2);
    }
}
