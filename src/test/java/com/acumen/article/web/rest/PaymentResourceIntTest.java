package com.acumen.article.web.rest;

import com.acumen.article.ArticleApp;

import com.acumen.article.domain.Payment;
import com.acumen.article.domain.Ordered;
import com.acumen.article.repository.PaymentRepository;
import com.acumen.article.service.PaymentService;
import com.acumen.article.repository.search.PaymentSearchRepository;
import com.acumen.article.web.rest.errors.ExceptionTranslator;
import com.acumen.article.service.dto.PaymentCriteria;
import com.acumen.article.service.PaymentQueryService;

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
import java.util.List;

import static com.acumen.article.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PaymentResource REST controller.
 *
 * @see PaymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleApp.class)
public class PaymentResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CART_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CART_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CARD_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_EXPIRY_MONTH = 1;
    private static final Integer UPDATED_EXPIRY_MONTH = 2;

    private static final Integer DEFAULT_EXPIRY_YEAR = 1;
    private static final Integer UPDATED_EXPIRY_YEAR = 2;

    private static final Integer DEFAULT_CVC = 1;
    private static final Integer UPDATED_CVC = 2;

    private static final String DEFAULT_HOLDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HOLDER_NAME = "BBBBBBBBBB";

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentSearchRepository paymentSearchRepository;

    @Autowired
    private PaymentQueryService paymentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaymentMockMvc;

    private Payment payment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentResource paymentResource = new PaymentResource(paymentService, paymentQueryService);
        this.restPaymentMockMvc = MockMvcBuilders.standaloneSetup(paymentResource)
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
    public static Payment createEntity(EntityManager em) {
        Payment payment = new Payment()
            .type(DEFAULT_TYPE)
            .cartName(DEFAULT_CART_NAME)
            .cardNumber(DEFAULT_CARD_NUMBER)
            .expiryMonth(DEFAULT_EXPIRY_MONTH)
            .expiryYear(DEFAULT_EXPIRY_YEAR)
            .cvc(DEFAULT_CVC)
            .holderName(DEFAULT_HOLDER_NAME);
        return payment;
    }

    @Before
    public void initTest() {
        paymentSearchRepository.deleteAll();
        payment = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // Create the Payment
        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPayment.getCartName()).isEqualTo(DEFAULT_CART_NAME);
        assertThat(testPayment.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testPayment.getExpiryMonth()).isEqualTo(DEFAULT_EXPIRY_MONTH);
        assertThat(testPayment.getExpiryYear()).isEqualTo(DEFAULT_EXPIRY_YEAR);
        assertThat(testPayment.getCvc()).isEqualTo(DEFAULT_CVC);
        assertThat(testPayment.getHolderName()).isEqualTo(DEFAULT_HOLDER_NAME);

        // Validate the Payment in Elasticsearch
        Payment paymentEs = paymentSearchRepository.findOne(testPayment.getId());
        assertThat(paymentEs).isEqualToIgnoringGivenFields(testPayment);
    }

    @Test
    @Transactional
    public void createPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // Create the Payment with an existing ID
        payment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setType(null);

        // Create the Payment, which fails.

        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCartNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setCartName(null);

        // Create the Payment, which fails.

        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCardNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setCardNumber(null);

        // Create the Payment, which fails.

        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpiryMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setExpiryMonth(null);

        // Create the Payment, which fails.

        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpiryYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setExpiryYear(null);

        // Create the Payment, which fails.

        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCvcIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setCvc(null);

        // Create the Payment, which fails.

        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHolderNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentRepository.findAll().size();
        // set the field null
        payment.setHolderName(null);

        // Create the Payment, which fails.

        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cartName").value(hasItem(DEFAULT_CART_NAME.toString())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].expiryMonth").value(hasItem(DEFAULT_EXPIRY_MONTH)))
            .andExpect(jsonPath("$.[*].expiryYear").value(hasItem(DEFAULT_EXPIRY_YEAR)))
            .andExpect(jsonPath("$.[*].cvc").value(hasItem(DEFAULT_CVC)))
            .andExpect(jsonPath("$.[*].holderName").value(hasItem(DEFAULT_HOLDER_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.cartName").value(DEFAULT_CART_NAME.toString()))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER.toString()))
            .andExpect(jsonPath("$.expiryMonth").value(DEFAULT_EXPIRY_MONTH))
            .andExpect(jsonPath("$.expiryYear").value(DEFAULT_EXPIRY_YEAR))
            .andExpect(jsonPath("$.cvc").value(DEFAULT_CVC))
            .andExpect(jsonPath("$.holderName").value(DEFAULT_HOLDER_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllPaymentsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where type equals to DEFAULT_TYPE
        defaultPaymentShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the paymentList where type equals to UPDATED_TYPE
        defaultPaymentShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultPaymentShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the paymentList where type equals to UPDATED_TYPE
        defaultPaymentShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where type is not null
        defaultPaymentShouldBeFound("type.specified=true");

        // Get all the paymentList where type is null
        defaultPaymentShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByCartNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where cartName equals to DEFAULT_CART_NAME
        defaultPaymentShouldBeFound("cartName.equals=" + DEFAULT_CART_NAME);

        // Get all the paymentList where cartName equals to UPDATED_CART_NAME
        defaultPaymentShouldNotBeFound("cartName.equals=" + UPDATED_CART_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentsByCartNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where cartName in DEFAULT_CART_NAME or UPDATED_CART_NAME
        defaultPaymentShouldBeFound("cartName.in=" + DEFAULT_CART_NAME + "," + UPDATED_CART_NAME);

        // Get all the paymentList where cartName equals to UPDATED_CART_NAME
        defaultPaymentShouldNotBeFound("cartName.in=" + UPDATED_CART_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentsByCartNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where cartName is not null
        defaultPaymentShouldBeFound("cartName.specified=true");

        // Get all the paymentList where cartName is null
        defaultPaymentShouldNotBeFound("cartName.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByCardNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where cardNumber equals to DEFAULT_CARD_NUMBER
        defaultPaymentShouldBeFound("cardNumber.equals=" + DEFAULT_CARD_NUMBER);

        // Get all the paymentList where cardNumber equals to UPDATED_CARD_NUMBER
        defaultPaymentShouldNotBeFound("cardNumber.equals=" + UPDATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPaymentsByCardNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where cardNumber in DEFAULT_CARD_NUMBER or UPDATED_CARD_NUMBER
        defaultPaymentShouldBeFound("cardNumber.in=" + DEFAULT_CARD_NUMBER + "," + UPDATED_CARD_NUMBER);

        // Get all the paymentList where cardNumber equals to UPDATED_CARD_NUMBER
        defaultPaymentShouldNotBeFound("cardNumber.in=" + UPDATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPaymentsByCardNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where cardNumber is not null
        defaultPaymentShouldBeFound("cardNumber.specified=true");

        // Get all the paymentList where cardNumber is null
        defaultPaymentShouldNotBeFound("cardNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByExpiryMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where expiryMonth equals to DEFAULT_EXPIRY_MONTH
        defaultPaymentShouldBeFound("expiryMonth.equals=" + DEFAULT_EXPIRY_MONTH);

        // Get all the paymentList where expiryMonth equals to UPDATED_EXPIRY_MONTH
        defaultPaymentShouldNotBeFound("expiryMonth.equals=" + UPDATED_EXPIRY_MONTH);
    }

    @Test
    @Transactional
    public void getAllPaymentsByExpiryMonthIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where expiryMonth in DEFAULT_EXPIRY_MONTH or UPDATED_EXPIRY_MONTH
        defaultPaymentShouldBeFound("expiryMonth.in=" + DEFAULT_EXPIRY_MONTH + "," + UPDATED_EXPIRY_MONTH);

        // Get all the paymentList where expiryMonth equals to UPDATED_EXPIRY_MONTH
        defaultPaymentShouldNotBeFound("expiryMonth.in=" + UPDATED_EXPIRY_MONTH);
    }

    @Test
    @Transactional
    public void getAllPaymentsByExpiryMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where expiryMonth is not null
        defaultPaymentShouldBeFound("expiryMonth.specified=true");

        // Get all the paymentList where expiryMonth is null
        defaultPaymentShouldNotBeFound("expiryMonth.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByExpiryMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where expiryMonth greater than or equals to DEFAULT_EXPIRY_MONTH
        defaultPaymentShouldBeFound("expiryMonth.greaterOrEqualThan=" + DEFAULT_EXPIRY_MONTH);

        // Get all the paymentList where expiryMonth greater than or equals to UPDATED_EXPIRY_MONTH
        defaultPaymentShouldNotBeFound("expiryMonth.greaterOrEqualThan=" + UPDATED_EXPIRY_MONTH);
    }

    @Test
    @Transactional
    public void getAllPaymentsByExpiryMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where expiryMonth less than or equals to DEFAULT_EXPIRY_MONTH
        defaultPaymentShouldNotBeFound("expiryMonth.lessThan=" + DEFAULT_EXPIRY_MONTH);

        // Get all the paymentList where expiryMonth less than or equals to UPDATED_EXPIRY_MONTH
        defaultPaymentShouldBeFound("expiryMonth.lessThan=" + UPDATED_EXPIRY_MONTH);
    }


    @Test
    @Transactional
    public void getAllPaymentsByExpiryYearIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where expiryYear equals to DEFAULT_EXPIRY_YEAR
        defaultPaymentShouldBeFound("expiryYear.equals=" + DEFAULT_EXPIRY_YEAR);

        // Get all the paymentList where expiryYear equals to UPDATED_EXPIRY_YEAR
        defaultPaymentShouldNotBeFound("expiryYear.equals=" + UPDATED_EXPIRY_YEAR);
    }

    @Test
    @Transactional
    public void getAllPaymentsByExpiryYearIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where expiryYear in DEFAULT_EXPIRY_YEAR or UPDATED_EXPIRY_YEAR
        defaultPaymentShouldBeFound("expiryYear.in=" + DEFAULT_EXPIRY_YEAR + "," + UPDATED_EXPIRY_YEAR);

        // Get all the paymentList where expiryYear equals to UPDATED_EXPIRY_YEAR
        defaultPaymentShouldNotBeFound("expiryYear.in=" + UPDATED_EXPIRY_YEAR);
    }

    @Test
    @Transactional
    public void getAllPaymentsByExpiryYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where expiryYear is not null
        defaultPaymentShouldBeFound("expiryYear.specified=true");

        // Get all the paymentList where expiryYear is null
        defaultPaymentShouldNotBeFound("expiryYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByExpiryYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where expiryYear greater than or equals to DEFAULT_EXPIRY_YEAR
        defaultPaymentShouldBeFound("expiryYear.greaterOrEqualThan=" + DEFAULT_EXPIRY_YEAR);

        // Get all the paymentList where expiryYear greater than or equals to UPDATED_EXPIRY_YEAR
        defaultPaymentShouldNotBeFound("expiryYear.greaterOrEqualThan=" + UPDATED_EXPIRY_YEAR);
    }

    @Test
    @Transactional
    public void getAllPaymentsByExpiryYearIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where expiryYear less than or equals to DEFAULT_EXPIRY_YEAR
        defaultPaymentShouldNotBeFound("expiryYear.lessThan=" + DEFAULT_EXPIRY_YEAR);

        // Get all the paymentList where expiryYear less than or equals to UPDATED_EXPIRY_YEAR
        defaultPaymentShouldBeFound("expiryYear.lessThan=" + UPDATED_EXPIRY_YEAR);
    }


    @Test
    @Transactional
    public void getAllPaymentsByCvcIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where cvc equals to DEFAULT_CVC
        defaultPaymentShouldBeFound("cvc.equals=" + DEFAULT_CVC);

        // Get all the paymentList where cvc equals to UPDATED_CVC
        defaultPaymentShouldNotBeFound("cvc.equals=" + UPDATED_CVC);
    }

    @Test
    @Transactional
    public void getAllPaymentsByCvcIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where cvc in DEFAULT_CVC or UPDATED_CVC
        defaultPaymentShouldBeFound("cvc.in=" + DEFAULT_CVC + "," + UPDATED_CVC);

        // Get all the paymentList where cvc equals to UPDATED_CVC
        defaultPaymentShouldNotBeFound("cvc.in=" + UPDATED_CVC);
    }

    @Test
    @Transactional
    public void getAllPaymentsByCvcIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where cvc is not null
        defaultPaymentShouldBeFound("cvc.specified=true");

        // Get all the paymentList where cvc is null
        defaultPaymentShouldNotBeFound("cvc.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByCvcIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where cvc greater than or equals to DEFAULT_CVC
        defaultPaymentShouldBeFound("cvc.greaterOrEqualThan=" + DEFAULT_CVC);

        // Get all the paymentList where cvc greater than or equals to UPDATED_CVC
        defaultPaymentShouldNotBeFound("cvc.greaterOrEqualThan=" + UPDATED_CVC);
    }

    @Test
    @Transactional
    public void getAllPaymentsByCvcIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where cvc less than or equals to DEFAULT_CVC
        defaultPaymentShouldNotBeFound("cvc.lessThan=" + DEFAULT_CVC);

        // Get all the paymentList where cvc less than or equals to UPDATED_CVC
        defaultPaymentShouldBeFound("cvc.lessThan=" + UPDATED_CVC);
    }


    @Test
    @Transactional
    public void getAllPaymentsByHolderNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where holderName equals to DEFAULT_HOLDER_NAME
        defaultPaymentShouldBeFound("holderName.equals=" + DEFAULT_HOLDER_NAME);

        // Get all the paymentList where holderName equals to UPDATED_HOLDER_NAME
        defaultPaymentShouldNotBeFound("holderName.equals=" + UPDATED_HOLDER_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentsByHolderNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where holderName in DEFAULT_HOLDER_NAME or UPDATED_HOLDER_NAME
        defaultPaymentShouldBeFound("holderName.in=" + DEFAULT_HOLDER_NAME + "," + UPDATED_HOLDER_NAME);

        // Get all the paymentList where holderName equals to UPDATED_HOLDER_NAME
        defaultPaymentShouldNotBeFound("holderName.in=" + UPDATED_HOLDER_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentsByHolderNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where holderName is not null
        defaultPaymentShouldBeFound("holderName.specified=true");

        // Get all the paymentList where holderName is null
        defaultPaymentShouldNotBeFound("holderName.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByOrderedIsEqualToSomething() throws Exception {
        // Initialize the database
        Ordered ordered = OrderedResourceIntTest.createEntity(em);
        em.persist(ordered);
        em.flush();
        payment.addOrdered(ordered);
        paymentRepository.saveAndFlush(payment);
        Long orderedId = ordered.getId();

        // Get all the paymentList where ordered equals to orderedId
        defaultPaymentShouldBeFound("orderedId.equals=" + orderedId);

        // Get all the paymentList where ordered equals to orderedId + 1
        defaultPaymentShouldNotBeFound("orderedId.equals=" + (orderedId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPaymentShouldBeFound(String filter) throws Exception {
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cartName").value(hasItem(DEFAULT_CART_NAME.toString())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].expiryMonth").value(hasItem(DEFAULT_EXPIRY_MONTH)))
            .andExpect(jsonPath("$.[*].expiryYear").value(hasItem(DEFAULT_EXPIRY_YEAR)))
            .andExpect(jsonPath("$.[*].cvc").value(hasItem(DEFAULT_CVC)))
            .andExpect(jsonPath("$.[*].holderName").value(hasItem(DEFAULT_HOLDER_NAME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPaymentShouldNotBeFound(String filter) throws Exception {
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayment() throws Exception {
        // Initialize the database
        paymentService.save(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        Payment updatedPayment = paymentRepository.findOne(payment.getId());
        // Disconnect from session so that the updates on updatedPayment are not directly saved in db
        em.detach(updatedPayment);
        updatedPayment
            .type(UPDATED_TYPE)
            .cartName(UPDATED_CART_NAME)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expiryMonth(UPDATED_EXPIRY_MONTH)
            .expiryYear(UPDATED_EXPIRY_YEAR)
            .cvc(UPDATED_CVC)
            .holderName(UPDATED_HOLDER_NAME);

        restPaymentMockMvc.perform(put("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayment)))
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPayment.getCartName()).isEqualTo(UPDATED_CART_NAME);
        assertThat(testPayment.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testPayment.getExpiryMonth()).isEqualTo(UPDATED_EXPIRY_MONTH);
        assertThat(testPayment.getExpiryYear()).isEqualTo(UPDATED_EXPIRY_YEAR);
        assertThat(testPayment.getCvc()).isEqualTo(UPDATED_CVC);
        assertThat(testPayment.getHolderName()).isEqualTo(UPDATED_HOLDER_NAME);

        // Validate the Payment in Elasticsearch
        Payment paymentEs = paymentSearchRepository.findOne(testPayment.getId());
        assertThat(paymentEs).isEqualToIgnoringGivenFields(testPayment);
    }

    @Test
    @Transactional
    public void updateNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Create the Payment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaymentMockMvc.perform(put("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePayment() throws Exception {
        // Initialize the database
        paymentService.save(payment);

        int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Get the payment
        restPaymentMockMvc.perform(delete("/api/payments/{id}", payment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean paymentExistsInEs = paymentSearchRepository.exists(payment.getId());
        assertThat(paymentExistsInEs).isFalse();

        // Validate the database is empty
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPayment() throws Exception {
        // Initialize the database
        paymentService.save(payment);

        // Search the payment
        restPaymentMockMvc.perform(get("/api/_search/payments?query=id:" + payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cartName").value(hasItem(DEFAULT_CART_NAME.toString())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].expiryMonth").value(hasItem(DEFAULT_EXPIRY_MONTH)))
            .andExpect(jsonPath("$.[*].expiryYear").value(hasItem(DEFAULT_EXPIRY_YEAR)))
            .andExpect(jsonPath("$.[*].cvc").value(hasItem(DEFAULT_CVC)))
            .andExpect(jsonPath("$.[*].holderName").value(hasItem(DEFAULT_HOLDER_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payment.class);
        Payment payment1 = new Payment();
        payment1.setId(1L);
        Payment payment2 = new Payment();
        payment2.setId(payment1.getId());
        assertThat(payment1).isEqualTo(payment2);
        payment2.setId(2L);
        assertThat(payment1).isNotEqualTo(payment2);
        payment1.setId(null);
        assertThat(payment1).isNotEqualTo(payment2);
    }
}
