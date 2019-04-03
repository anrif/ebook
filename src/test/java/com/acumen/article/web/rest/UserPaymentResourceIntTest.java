package com.acumen.article.web.rest;

import com.acumen.article.ArticleApp;

import com.acumen.article.domain.UserPayment;
import com.acumen.article.domain.User;
import com.acumen.article.repository.UserPaymentRepository;
import com.acumen.article.service.UserPaymentService;
import com.acumen.article.repository.search.UserPaymentSearchRepository;
import com.acumen.article.web.rest.errors.ExceptionTranslator;
import com.acumen.article.service.dto.UserPaymentCriteria;
import com.acumen.article.service.UserPaymentQueryService;

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

import com.acumen.article.domain.enumeration.TypePayment;
/**
 * Test class for the UserPaymentResource REST controller.
 *
 * @see UserPaymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleApp.class)
public class UserPaymentResourceIntTest {

    private static final TypePayment DEFAULT_TYPE = TypePayment.VISA;
    private static final TypePayment UPDATED_TYPE = TypePayment.PAYPAL;

    private static final String DEFAULT_CARD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CARD_NAME = "BBBBBBBBBB";

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

    private static final Boolean DEFAULT_DEFAULT_PAYMENT = false;
    private static final Boolean UPDATED_DEFAULT_PAYMENT = true;

    @Autowired
    private UserPaymentRepository userPaymentRepository;

    @Autowired
    private UserPaymentService userPaymentService;

    @Autowired
    private UserPaymentSearchRepository userPaymentSearchRepository;

    @Autowired
    private UserPaymentQueryService userPaymentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserPaymentMockMvc;

    private UserPayment userPayment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserPaymentResource userPaymentResource = new UserPaymentResource(userPaymentService, userPaymentQueryService);
        this.restUserPaymentMockMvc = MockMvcBuilders.standaloneSetup(userPaymentResource)
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
    public static UserPayment createEntity(EntityManager em) {
        UserPayment userPayment = new UserPayment()
            .type(DEFAULT_TYPE)
            .cardName(DEFAULT_CARD_NAME)
            .cardNumber(DEFAULT_CARD_NUMBER)
            .expiryMonth(DEFAULT_EXPIRY_MONTH)
            .expiryYear(DEFAULT_EXPIRY_YEAR)
            .cvc(DEFAULT_CVC)
            .holderName(DEFAULT_HOLDER_NAME)
            .defaultPayment(DEFAULT_DEFAULT_PAYMENT);
        return userPayment;
    }

    @Before
    public void initTest() {
        userPaymentSearchRepository.deleteAll();
        userPayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserPayment() throws Exception {
        int databaseSizeBeforeCreate = userPaymentRepository.findAll().size();

        // Create the UserPayment
        restUserPaymentMockMvc.perform(post("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPayment)))
            .andExpect(status().isCreated());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        UserPayment testUserPayment = userPaymentList.get(userPaymentList.size() - 1);
        assertThat(testUserPayment.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testUserPayment.getCardName()).isEqualTo(DEFAULT_CARD_NAME);
        assertThat(testUserPayment.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testUserPayment.getExpiryMonth()).isEqualTo(DEFAULT_EXPIRY_MONTH);
        assertThat(testUserPayment.getExpiryYear()).isEqualTo(DEFAULT_EXPIRY_YEAR);
        assertThat(testUserPayment.getCvc()).isEqualTo(DEFAULT_CVC);
        assertThat(testUserPayment.getHolderName()).isEqualTo(DEFAULT_HOLDER_NAME);
        assertThat(testUserPayment.isDefaultPayment()).isEqualTo(DEFAULT_DEFAULT_PAYMENT);

        // Validate the UserPayment in Elasticsearch
        UserPayment userPaymentEs = userPaymentSearchRepository.findOne(testUserPayment.getId());
        assertThat(userPaymentEs).isEqualToIgnoringGivenFields(testUserPayment);
    }

    @Test
    @Transactional
    public void createUserPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPaymentRepository.findAll().size();

        // Create the UserPayment with an existing ID
        userPayment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPaymentMockMvc.perform(post("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPayment)))
            .andExpect(status().isBadRequest());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentRepository.findAll().size();
        // set the field null
        userPayment.setType(null);

        // Create the UserPayment, which fails.

        restUserPaymentMockMvc.perform(post("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPayment)))
            .andExpect(status().isBadRequest());

        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCardNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentRepository.findAll().size();
        // set the field null
        userPayment.setCardName(null);

        // Create the UserPayment, which fails.

        restUserPaymentMockMvc.perform(post("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPayment)))
            .andExpect(status().isBadRequest());

        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCardNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentRepository.findAll().size();
        // set the field null
        userPayment.setCardNumber(null);

        // Create the UserPayment, which fails.

        restUserPaymentMockMvc.perform(post("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPayment)))
            .andExpect(status().isBadRequest());

        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpiryMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentRepository.findAll().size();
        // set the field null
        userPayment.setExpiryMonth(null);

        // Create the UserPayment, which fails.

        restUserPaymentMockMvc.perform(post("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPayment)))
            .andExpect(status().isBadRequest());

        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpiryYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentRepository.findAll().size();
        // set the field null
        userPayment.setExpiryYear(null);

        // Create the UserPayment, which fails.

        restUserPaymentMockMvc.perform(post("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPayment)))
            .andExpect(status().isBadRequest());

        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCvcIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentRepository.findAll().size();
        // set the field null
        userPayment.setCvc(null);

        // Create the UserPayment, which fails.

        restUserPaymentMockMvc.perform(post("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPayment)))
            .andExpect(status().isBadRequest());

        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHolderNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPaymentRepository.findAll().size();
        // set the field null
        userPayment.setHolderName(null);

        // Create the UserPayment, which fails.

        restUserPaymentMockMvc.perform(post("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPayment)))
            .andExpect(status().isBadRequest());

        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserPayments() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList
        restUserPaymentMockMvc.perform(get("/api/user-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cardName").value(hasItem(DEFAULT_CARD_NAME.toString())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].expiryMonth").value(hasItem(DEFAULT_EXPIRY_MONTH)))
            .andExpect(jsonPath("$.[*].expiryYear").value(hasItem(DEFAULT_EXPIRY_YEAR)))
            .andExpect(jsonPath("$.[*].cvc").value(hasItem(DEFAULT_CVC)))
            .andExpect(jsonPath("$.[*].holderName").value(hasItem(DEFAULT_HOLDER_NAME.toString())))
            .andExpect(jsonPath("$.[*].defaultPayment").value(hasItem(DEFAULT_DEFAULT_PAYMENT.booleanValue())));
    }

    @Test
    @Transactional
    public void getUserPayment() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get the userPayment
        restUserPaymentMockMvc.perform(get("/api/user-payments/{id}", userPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userPayment.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.cardName").value(DEFAULT_CARD_NAME.toString()))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER.toString()))
            .andExpect(jsonPath("$.expiryMonth").value(DEFAULT_EXPIRY_MONTH))
            .andExpect(jsonPath("$.expiryYear").value(DEFAULT_EXPIRY_YEAR))
            .andExpect(jsonPath("$.cvc").value(DEFAULT_CVC))
            .andExpect(jsonPath("$.holderName").value(DEFAULT_HOLDER_NAME.toString()))
            .andExpect(jsonPath("$.defaultPayment").value(DEFAULT_DEFAULT_PAYMENT.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where type equals to DEFAULT_TYPE
        defaultUserPaymentShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the userPaymentList where type equals to UPDATED_TYPE
        defaultUserPaymentShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultUserPaymentShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the userPaymentList where type equals to UPDATED_TYPE
        defaultUserPaymentShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where type is not null
        defaultUserPaymentShouldBeFound("type.specified=true");

        // Get all the userPaymentList where type is null
        defaultUserPaymentShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByCardNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where cardName equals to DEFAULT_CARD_NAME
        defaultUserPaymentShouldBeFound("cardName.equals=" + DEFAULT_CARD_NAME);

        // Get all the userPaymentList where cardName equals to UPDATED_CARD_NAME
        defaultUserPaymentShouldNotBeFound("cardName.equals=" + UPDATED_CARD_NAME);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByCardNameIsInShouldWork() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where cardName in DEFAULT_CARD_NAME or UPDATED_CARD_NAME
        defaultUserPaymentShouldBeFound("cardName.in=" + DEFAULT_CARD_NAME + "," + UPDATED_CARD_NAME);

        // Get all the userPaymentList where cardName equals to UPDATED_CARD_NAME
        defaultUserPaymentShouldNotBeFound("cardName.in=" + UPDATED_CARD_NAME);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByCardNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where cardName is not null
        defaultUserPaymentShouldBeFound("cardName.specified=true");

        // Get all the userPaymentList where cardName is null
        defaultUserPaymentShouldNotBeFound("cardName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByCardNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where cardNumber equals to DEFAULT_CARD_NUMBER
        defaultUserPaymentShouldBeFound("cardNumber.equals=" + DEFAULT_CARD_NUMBER);

        // Get all the userPaymentList where cardNumber equals to UPDATED_CARD_NUMBER
        defaultUserPaymentShouldNotBeFound("cardNumber.equals=" + UPDATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByCardNumberIsInShouldWork() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where cardNumber in DEFAULT_CARD_NUMBER or UPDATED_CARD_NUMBER
        defaultUserPaymentShouldBeFound("cardNumber.in=" + DEFAULT_CARD_NUMBER + "," + UPDATED_CARD_NUMBER);

        // Get all the userPaymentList where cardNumber equals to UPDATED_CARD_NUMBER
        defaultUserPaymentShouldNotBeFound("cardNumber.in=" + UPDATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByCardNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where cardNumber is not null
        defaultUserPaymentShouldBeFound("cardNumber.specified=true");

        // Get all the userPaymentList where cardNumber is null
        defaultUserPaymentShouldNotBeFound("cardNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByExpiryMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where expiryMonth equals to DEFAULT_EXPIRY_MONTH
        defaultUserPaymentShouldBeFound("expiryMonth.equals=" + DEFAULT_EXPIRY_MONTH);

        // Get all the userPaymentList where expiryMonth equals to UPDATED_EXPIRY_MONTH
        defaultUserPaymentShouldNotBeFound("expiryMonth.equals=" + UPDATED_EXPIRY_MONTH);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByExpiryMonthIsInShouldWork() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where expiryMonth in DEFAULT_EXPIRY_MONTH or UPDATED_EXPIRY_MONTH
        defaultUserPaymentShouldBeFound("expiryMonth.in=" + DEFAULT_EXPIRY_MONTH + "," + UPDATED_EXPIRY_MONTH);

        // Get all the userPaymentList where expiryMonth equals to UPDATED_EXPIRY_MONTH
        defaultUserPaymentShouldNotBeFound("expiryMonth.in=" + UPDATED_EXPIRY_MONTH);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByExpiryMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where expiryMonth is not null
        defaultUserPaymentShouldBeFound("expiryMonth.specified=true");

        // Get all the userPaymentList where expiryMonth is null
        defaultUserPaymentShouldNotBeFound("expiryMonth.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByExpiryMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where expiryMonth greater than or equals to DEFAULT_EXPIRY_MONTH
        defaultUserPaymentShouldBeFound("expiryMonth.greaterOrEqualThan=" + DEFAULT_EXPIRY_MONTH);

        // Get all the userPaymentList where expiryMonth greater than or equals to UPDATED_EXPIRY_MONTH
        defaultUserPaymentShouldNotBeFound("expiryMonth.greaterOrEqualThan=" + UPDATED_EXPIRY_MONTH);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByExpiryMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where expiryMonth less than or equals to DEFAULT_EXPIRY_MONTH
        defaultUserPaymentShouldNotBeFound("expiryMonth.lessThan=" + DEFAULT_EXPIRY_MONTH);

        // Get all the userPaymentList where expiryMonth less than or equals to UPDATED_EXPIRY_MONTH
        defaultUserPaymentShouldBeFound("expiryMonth.lessThan=" + UPDATED_EXPIRY_MONTH);
    }


    @Test
    @Transactional
    public void getAllUserPaymentsByExpiryYearIsEqualToSomething() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where expiryYear equals to DEFAULT_EXPIRY_YEAR
        defaultUserPaymentShouldBeFound("expiryYear.equals=" + DEFAULT_EXPIRY_YEAR);

        // Get all the userPaymentList where expiryYear equals to UPDATED_EXPIRY_YEAR
        defaultUserPaymentShouldNotBeFound("expiryYear.equals=" + UPDATED_EXPIRY_YEAR);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByExpiryYearIsInShouldWork() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where expiryYear in DEFAULT_EXPIRY_YEAR or UPDATED_EXPIRY_YEAR
        defaultUserPaymentShouldBeFound("expiryYear.in=" + DEFAULT_EXPIRY_YEAR + "," + UPDATED_EXPIRY_YEAR);

        // Get all the userPaymentList where expiryYear equals to UPDATED_EXPIRY_YEAR
        defaultUserPaymentShouldNotBeFound("expiryYear.in=" + UPDATED_EXPIRY_YEAR);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByExpiryYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where expiryYear is not null
        defaultUserPaymentShouldBeFound("expiryYear.specified=true");

        // Get all the userPaymentList where expiryYear is null
        defaultUserPaymentShouldNotBeFound("expiryYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByExpiryYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where expiryYear greater than or equals to DEFAULT_EXPIRY_YEAR
        defaultUserPaymentShouldBeFound("expiryYear.greaterOrEqualThan=" + DEFAULT_EXPIRY_YEAR);

        // Get all the userPaymentList where expiryYear greater than or equals to UPDATED_EXPIRY_YEAR
        defaultUserPaymentShouldNotBeFound("expiryYear.greaterOrEqualThan=" + UPDATED_EXPIRY_YEAR);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByExpiryYearIsLessThanSomething() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where expiryYear less than or equals to DEFAULT_EXPIRY_YEAR
        defaultUserPaymentShouldNotBeFound("expiryYear.lessThan=" + DEFAULT_EXPIRY_YEAR);

        // Get all the userPaymentList where expiryYear less than or equals to UPDATED_EXPIRY_YEAR
        defaultUserPaymentShouldBeFound("expiryYear.lessThan=" + UPDATED_EXPIRY_YEAR);
    }


    @Test
    @Transactional
    public void getAllUserPaymentsByCvcIsEqualToSomething() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where cvc equals to DEFAULT_CVC
        defaultUserPaymentShouldBeFound("cvc.equals=" + DEFAULT_CVC);

        // Get all the userPaymentList where cvc equals to UPDATED_CVC
        defaultUserPaymentShouldNotBeFound("cvc.equals=" + UPDATED_CVC);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByCvcIsInShouldWork() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where cvc in DEFAULT_CVC or UPDATED_CVC
        defaultUserPaymentShouldBeFound("cvc.in=" + DEFAULT_CVC + "," + UPDATED_CVC);

        // Get all the userPaymentList where cvc equals to UPDATED_CVC
        defaultUserPaymentShouldNotBeFound("cvc.in=" + UPDATED_CVC);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByCvcIsNullOrNotNull() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where cvc is not null
        defaultUserPaymentShouldBeFound("cvc.specified=true");

        // Get all the userPaymentList where cvc is null
        defaultUserPaymentShouldNotBeFound("cvc.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByCvcIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where cvc greater than or equals to DEFAULT_CVC
        defaultUserPaymentShouldBeFound("cvc.greaterOrEqualThan=" + DEFAULT_CVC);

        // Get all the userPaymentList where cvc greater than or equals to UPDATED_CVC
        defaultUserPaymentShouldNotBeFound("cvc.greaterOrEqualThan=" + UPDATED_CVC);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByCvcIsLessThanSomething() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where cvc less than or equals to DEFAULT_CVC
        defaultUserPaymentShouldNotBeFound("cvc.lessThan=" + DEFAULT_CVC);

        // Get all the userPaymentList where cvc less than or equals to UPDATED_CVC
        defaultUserPaymentShouldBeFound("cvc.lessThan=" + UPDATED_CVC);
    }


    @Test
    @Transactional
    public void getAllUserPaymentsByHolderNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where holderName equals to DEFAULT_HOLDER_NAME
        defaultUserPaymentShouldBeFound("holderName.equals=" + DEFAULT_HOLDER_NAME);

        // Get all the userPaymentList where holderName equals to UPDATED_HOLDER_NAME
        defaultUserPaymentShouldNotBeFound("holderName.equals=" + UPDATED_HOLDER_NAME);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByHolderNameIsInShouldWork() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where holderName in DEFAULT_HOLDER_NAME or UPDATED_HOLDER_NAME
        defaultUserPaymentShouldBeFound("holderName.in=" + DEFAULT_HOLDER_NAME + "," + UPDATED_HOLDER_NAME);

        // Get all the userPaymentList where holderName equals to UPDATED_HOLDER_NAME
        defaultUserPaymentShouldNotBeFound("holderName.in=" + UPDATED_HOLDER_NAME);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByHolderNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where holderName is not null
        defaultUserPaymentShouldBeFound("holderName.specified=true");

        // Get all the userPaymentList where holderName is null
        defaultUserPaymentShouldNotBeFound("holderName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByDefaultPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where defaultPayment equals to DEFAULT_DEFAULT_PAYMENT
        defaultUserPaymentShouldBeFound("defaultPayment.equals=" + DEFAULT_DEFAULT_PAYMENT);

        // Get all the userPaymentList where defaultPayment equals to UPDATED_DEFAULT_PAYMENT
        defaultUserPaymentShouldNotBeFound("defaultPayment.equals=" + UPDATED_DEFAULT_PAYMENT);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByDefaultPaymentIsInShouldWork() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where defaultPayment in DEFAULT_DEFAULT_PAYMENT or UPDATED_DEFAULT_PAYMENT
        defaultUserPaymentShouldBeFound("defaultPayment.in=" + DEFAULT_DEFAULT_PAYMENT + "," + UPDATED_DEFAULT_PAYMENT);

        // Get all the userPaymentList where defaultPayment equals to UPDATED_DEFAULT_PAYMENT
        defaultUserPaymentShouldNotBeFound("defaultPayment.in=" + UPDATED_DEFAULT_PAYMENT);
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByDefaultPaymentIsNullOrNotNull() throws Exception {
        // Initialize the database
        userPaymentRepository.saveAndFlush(userPayment);

        // Get all the userPaymentList where defaultPayment is not null
        defaultUserPaymentShouldBeFound("defaultPayment.specified=true");

        // Get all the userPaymentList where defaultPayment is null
        defaultUserPaymentShouldNotBeFound("defaultPayment.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserPaymentsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        userPayment.setUser(user);
        userPaymentRepository.saveAndFlush(userPayment);
        Long userId = user.getId();

        // Get all the userPaymentList where user equals to userId
        defaultUserPaymentShouldBeFound("userId.equals=" + userId);

        // Get all the userPaymentList where user equals to userId + 1
        defaultUserPaymentShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultUserPaymentShouldBeFound(String filter) throws Exception {
        restUserPaymentMockMvc.perform(get("/api/user-payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cardName").value(hasItem(DEFAULT_CARD_NAME.toString())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].expiryMonth").value(hasItem(DEFAULT_EXPIRY_MONTH)))
            .andExpect(jsonPath("$.[*].expiryYear").value(hasItem(DEFAULT_EXPIRY_YEAR)))
            .andExpect(jsonPath("$.[*].cvc").value(hasItem(DEFAULT_CVC)))
            .andExpect(jsonPath("$.[*].holderName").value(hasItem(DEFAULT_HOLDER_NAME.toString())))
            .andExpect(jsonPath("$.[*].defaultPayment").value(hasItem(DEFAULT_DEFAULT_PAYMENT.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultUserPaymentShouldNotBeFound(String filter) throws Exception {
        restUserPaymentMockMvc.perform(get("/api/user-payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingUserPayment() throws Exception {
        // Get the userPayment
        restUserPaymentMockMvc.perform(get("/api/user-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserPayment() throws Exception {
        // Initialize the database
        userPaymentService.save(userPayment);

        int databaseSizeBeforeUpdate = userPaymentRepository.findAll().size();

        // Update the userPayment
        UserPayment updatedUserPayment = userPaymentRepository.findOne(userPayment.getId());
        // Disconnect from session so that the updates on updatedUserPayment are not directly saved in db
        em.detach(updatedUserPayment);
        updatedUserPayment
            .type(UPDATED_TYPE)
            .cardName(UPDATED_CARD_NAME)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expiryMonth(UPDATED_EXPIRY_MONTH)
            .expiryYear(UPDATED_EXPIRY_YEAR)
            .cvc(UPDATED_CVC)
            .holderName(UPDATED_HOLDER_NAME)
            .defaultPayment(UPDATED_DEFAULT_PAYMENT);

        restUserPaymentMockMvc.perform(put("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserPayment)))
            .andExpect(status().isOk());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeUpdate);
        UserPayment testUserPayment = userPaymentList.get(userPaymentList.size() - 1);
        assertThat(testUserPayment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testUserPayment.getCardName()).isEqualTo(UPDATED_CARD_NAME);
        assertThat(testUserPayment.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testUserPayment.getExpiryMonth()).isEqualTo(UPDATED_EXPIRY_MONTH);
        assertThat(testUserPayment.getExpiryYear()).isEqualTo(UPDATED_EXPIRY_YEAR);
        assertThat(testUserPayment.getCvc()).isEqualTo(UPDATED_CVC);
        assertThat(testUserPayment.getHolderName()).isEqualTo(UPDATED_HOLDER_NAME);
        assertThat(testUserPayment.isDefaultPayment()).isEqualTo(UPDATED_DEFAULT_PAYMENT);

        // Validate the UserPayment in Elasticsearch
        UserPayment userPaymentEs = userPaymentSearchRepository.findOne(testUserPayment.getId());
        assertThat(userPaymentEs).isEqualToIgnoringGivenFields(testUserPayment);
    }

    @Test
    @Transactional
    public void updateNonExistingUserPayment() throws Exception {
        int databaseSizeBeforeUpdate = userPaymentRepository.findAll().size();

        // Create the UserPayment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserPaymentMockMvc.perform(put("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPayment)))
            .andExpect(status().isCreated());

        // Validate the UserPayment in the database
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserPayment() throws Exception {
        // Initialize the database
        userPaymentService.save(userPayment);

        int databaseSizeBeforeDelete = userPaymentRepository.findAll().size();

        // Get the userPayment
        restUserPaymentMockMvc.perform(delete("/api/user-payments/{id}", userPayment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userPaymentExistsInEs = userPaymentSearchRepository.exists(userPayment.getId());
        assertThat(userPaymentExistsInEs).isFalse();

        // Validate the database is empty
        List<UserPayment> userPaymentList = userPaymentRepository.findAll();
        assertThat(userPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserPayment() throws Exception {
        // Initialize the database
        userPaymentService.save(userPayment);

        // Search the userPayment
        restUserPaymentMockMvc.perform(get("/api/_search/user-payments?query=id:" + userPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cardName").value(hasItem(DEFAULT_CARD_NAME.toString())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].expiryMonth").value(hasItem(DEFAULT_EXPIRY_MONTH)))
            .andExpect(jsonPath("$.[*].expiryYear").value(hasItem(DEFAULT_EXPIRY_YEAR)))
            .andExpect(jsonPath("$.[*].cvc").value(hasItem(DEFAULT_CVC)))
            .andExpect(jsonPath("$.[*].holderName").value(hasItem(DEFAULT_HOLDER_NAME.toString())))
            .andExpect(jsonPath("$.[*].defaultPayment").value(hasItem(DEFAULT_DEFAULT_PAYMENT.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPayment.class);
        UserPayment userPayment1 = new UserPayment();
        userPayment1.setId(1L);
        UserPayment userPayment2 = new UserPayment();
        userPayment2.setId(userPayment1.getId());
        assertThat(userPayment1).isEqualTo(userPayment2);
        userPayment2.setId(2L);
        assertThat(userPayment1).isNotEqualTo(userPayment2);
        userPayment1.setId(null);
        assertThat(userPayment1).isNotEqualTo(userPayment2);
    }
}
