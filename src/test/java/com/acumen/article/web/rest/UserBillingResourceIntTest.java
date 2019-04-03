package com.acumen.article.web.rest;

import com.acumen.article.ArticleApp;

import com.acumen.article.domain.UserBilling;
import com.acumen.article.domain.User;
import com.acumen.article.repository.UserBillingRepository;
import com.acumen.article.service.UserBillingService;
import com.acumen.article.repository.search.UserBillingSearchRepository;
import com.acumen.article.web.rest.errors.ExceptionTranslator;
import com.acumen.article.service.dto.UserBillingCriteria;
import com.acumen.article.service.UserBillingQueryService;

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
 * Test class for the UserBillingResource REST controller.
 *
 * @see UserBillingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleApp.class)
public class UserBillingResourceIntTest {

    private static final String DEFAULT_USER_BILLING_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_BILLING_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_BILLING_STREET_1 = "AAAAAAAAAA";
    private static final String UPDATED_USER_BILLING_STREET_1 = "BBBBBBBBBB";

    private static final String DEFAULT_USER_BILLING_STREET_2 = "AAAAAAAAAA";
    private static final String UPDATED_USER_BILLING_STREET_2 = "BBBBBBBBBB";

    private static final String DEFAULT_USER_BILLING_CITY = "AAAAAAAAAA";
    private static final String UPDATED_USER_BILLING_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_USER_BILLING_STATE = "AAAAAAAAAA";
    private static final String UPDATED_USER_BILLING_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_USER_BILLING_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_USER_BILLING_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_USER_BILLING_ZIPCODE = "AAAAAAAAAA";
    private static final String UPDATED_USER_BILLING_ZIPCODE = "BBBBBBBBBB";

    @Autowired
    private UserBillingRepository userBillingRepository;

    @Autowired
    private UserBillingService userBillingService;

    @Autowired
    private UserBillingSearchRepository userBillingSearchRepository;

    @Autowired
    private UserBillingQueryService userBillingQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserBillingMockMvc;

    private UserBilling userBilling;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserBillingResource userBillingResource = new UserBillingResource(userBillingService, userBillingQueryService);
        this.restUserBillingMockMvc = MockMvcBuilders.standaloneSetup(userBillingResource)
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
    public static UserBilling createEntity(EntityManager em) {
        UserBilling userBilling = new UserBilling()
            .userBillingName(DEFAULT_USER_BILLING_NAME)
            .userBillingStreet1(DEFAULT_USER_BILLING_STREET_1)
            .userBillingStreet2(DEFAULT_USER_BILLING_STREET_2)
            .userBillingCity(DEFAULT_USER_BILLING_CITY)
            .userBillingState(DEFAULT_USER_BILLING_STATE)
            .userBillingCountry(DEFAULT_USER_BILLING_COUNTRY)
            .userBillingZipcode(DEFAULT_USER_BILLING_ZIPCODE);
        return userBilling;
    }

    @Before
    public void initTest() {
        userBillingSearchRepository.deleteAll();
        userBilling = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserBilling() throws Exception {
        int databaseSizeBeforeCreate = userBillingRepository.findAll().size();

        // Create the UserBilling
        restUserBillingMockMvc.perform(post("/api/user-billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBilling)))
            .andExpect(status().isCreated());

        // Validate the UserBilling in the database
        List<UserBilling> userBillingList = userBillingRepository.findAll();
        assertThat(userBillingList).hasSize(databaseSizeBeforeCreate + 1);
        UserBilling testUserBilling = userBillingList.get(userBillingList.size() - 1);
        assertThat(testUserBilling.getUserBillingName()).isEqualTo(DEFAULT_USER_BILLING_NAME);
        assertThat(testUserBilling.getUserBillingStreet1()).isEqualTo(DEFAULT_USER_BILLING_STREET_1);
        assertThat(testUserBilling.getUserBillingStreet2()).isEqualTo(DEFAULT_USER_BILLING_STREET_2);
        assertThat(testUserBilling.getUserBillingCity()).isEqualTo(DEFAULT_USER_BILLING_CITY);
        assertThat(testUserBilling.getUserBillingState()).isEqualTo(DEFAULT_USER_BILLING_STATE);
        assertThat(testUserBilling.getUserBillingCountry()).isEqualTo(DEFAULT_USER_BILLING_COUNTRY);
        assertThat(testUserBilling.getUserBillingZipcode()).isEqualTo(DEFAULT_USER_BILLING_ZIPCODE);

        // Validate the UserBilling in Elasticsearch
        UserBilling userBillingEs = userBillingSearchRepository.findOne(testUserBilling.getId());
        assertThat(userBillingEs).isEqualToIgnoringGivenFields(testUserBilling);
    }

    @Test
    @Transactional
    public void createUserBillingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userBillingRepository.findAll().size();

        // Create the UserBilling with an existing ID
        userBilling.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserBillingMockMvc.perform(post("/api/user-billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBilling)))
            .andExpect(status().isBadRequest());

        // Validate the UserBilling in the database
        List<UserBilling> userBillingList = userBillingRepository.findAll();
        assertThat(userBillingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserBillingNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userBillingRepository.findAll().size();
        // set the field null
        userBilling.setUserBillingName(null);

        // Create the UserBilling, which fails.

        restUserBillingMockMvc.perform(post("/api/user-billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBilling)))
            .andExpect(status().isBadRequest());

        List<UserBilling> userBillingList = userBillingRepository.findAll();
        assertThat(userBillingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserBillingCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = userBillingRepository.findAll().size();
        // set the field null
        userBilling.setUserBillingCity(null);

        // Create the UserBilling, which fails.

        restUserBillingMockMvc.perform(post("/api/user-billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBilling)))
            .andExpect(status().isBadRequest());

        List<UserBilling> userBillingList = userBillingRepository.findAll();
        assertThat(userBillingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserBillingStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userBillingRepository.findAll().size();
        // set the field null
        userBilling.setUserBillingState(null);

        // Create the UserBilling, which fails.

        restUserBillingMockMvc.perform(post("/api/user-billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBilling)))
            .andExpect(status().isBadRequest());

        List<UserBilling> userBillingList = userBillingRepository.findAll();
        assertThat(userBillingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserBillingCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = userBillingRepository.findAll().size();
        // set the field null
        userBilling.setUserBillingCountry(null);

        // Create the UserBilling, which fails.

        restUserBillingMockMvc.perform(post("/api/user-billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBilling)))
            .andExpect(status().isBadRequest());

        List<UserBilling> userBillingList = userBillingRepository.findAll();
        assertThat(userBillingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserBillings() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList
        restUserBillingMockMvc.perform(get("/api/user-billings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userBilling.getId().intValue())))
            .andExpect(jsonPath("$.[*].userBillingName").value(hasItem(DEFAULT_USER_BILLING_NAME.toString())))
            .andExpect(jsonPath("$.[*].userBillingStreet1").value(hasItem(DEFAULT_USER_BILLING_STREET_1.toString())))
            .andExpect(jsonPath("$.[*].userBillingStreet2").value(hasItem(DEFAULT_USER_BILLING_STREET_2.toString())))
            .andExpect(jsonPath("$.[*].userBillingCity").value(hasItem(DEFAULT_USER_BILLING_CITY.toString())))
            .andExpect(jsonPath("$.[*].userBillingState").value(hasItem(DEFAULT_USER_BILLING_STATE.toString())))
            .andExpect(jsonPath("$.[*].userBillingCountry").value(hasItem(DEFAULT_USER_BILLING_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].userBillingZipcode").value(hasItem(DEFAULT_USER_BILLING_ZIPCODE.toString())));
    }

    @Test
    @Transactional
    public void getUserBilling() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get the userBilling
        restUserBillingMockMvc.perform(get("/api/user-billings/{id}", userBilling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userBilling.getId().intValue()))
            .andExpect(jsonPath("$.userBillingName").value(DEFAULT_USER_BILLING_NAME.toString()))
            .andExpect(jsonPath("$.userBillingStreet1").value(DEFAULT_USER_BILLING_STREET_1.toString()))
            .andExpect(jsonPath("$.userBillingStreet2").value(DEFAULT_USER_BILLING_STREET_2.toString()))
            .andExpect(jsonPath("$.userBillingCity").value(DEFAULT_USER_BILLING_CITY.toString()))
            .andExpect(jsonPath("$.userBillingState").value(DEFAULT_USER_BILLING_STATE.toString()))
            .andExpect(jsonPath("$.userBillingCountry").value(DEFAULT_USER_BILLING_COUNTRY.toString()))
            .andExpect(jsonPath("$.userBillingZipcode").value(DEFAULT_USER_BILLING_ZIPCODE.toString()));
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingName equals to DEFAULT_USER_BILLING_NAME
        defaultUserBillingShouldBeFound("userBillingName.equals=" + DEFAULT_USER_BILLING_NAME);

        // Get all the userBillingList where userBillingName equals to UPDATED_USER_BILLING_NAME
        defaultUserBillingShouldNotBeFound("userBillingName.equals=" + UPDATED_USER_BILLING_NAME);
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingNameIsInShouldWork() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingName in DEFAULT_USER_BILLING_NAME or UPDATED_USER_BILLING_NAME
        defaultUserBillingShouldBeFound("userBillingName.in=" + DEFAULT_USER_BILLING_NAME + "," + UPDATED_USER_BILLING_NAME);

        // Get all the userBillingList where userBillingName equals to UPDATED_USER_BILLING_NAME
        defaultUserBillingShouldNotBeFound("userBillingName.in=" + UPDATED_USER_BILLING_NAME);
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingName is not null
        defaultUserBillingShouldBeFound("userBillingName.specified=true");

        // Get all the userBillingList where userBillingName is null
        defaultUserBillingShouldNotBeFound("userBillingName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingStreet1IsEqualToSomething() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingStreet1 equals to DEFAULT_USER_BILLING_STREET_1
        defaultUserBillingShouldBeFound("userBillingStreet1.equals=" + DEFAULT_USER_BILLING_STREET_1);

        // Get all the userBillingList where userBillingStreet1 equals to UPDATED_USER_BILLING_STREET_1
        defaultUserBillingShouldNotBeFound("userBillingStreet1.equals=" + UPDATED_USER_BILLING_STREET_1);
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingStreet1IsInShouldWork() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingStreet1 in DEFAULT_USER_BILLING_STREET_1 or UPDATED_USER_BILLING_STREET_1
        defaultUserBillingShouldBeFound("userBillingStreet1.in=" + DEFAULT_USER_BILLING_STREET_1 + "," + UPDATED_USER_BILLING_STREET_1);

        // Get all the userBillingList where userBillingStreet1 equals to UPDATED_USER_BILLING_STREET_1
        defaultUserBillingShouldNotBeFound("userBillingStreet1.in=" + UPDATED_USER_BILLING_STREET_1);
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingStreet1IsNullOrNotNull() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingStreet1 is not null
        defaultUserBillingShouldBeFound("userBillingStreet1.specified=true");

        // Get all the userBillingList where userBillingStreet1 is null
        defaultUserBillingShouldNotBeFound("userBillingStreet1.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingStreet2IsEqualToSomething() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingStreet2 equals to DEFAULT_USER_BILLING_STREET_2
        defaultUserBillingShouldBeFound("userBillingStreet2.equals=" + DEFAULT_USER_BILLING_STREET_2);

        // Get all the userBillingList where userBillingStreet2 equals to UPDATED_USER_BILLING_STREET_2
        defaultUserBillingShouldNotBeFound("userBillingStreet2.equals=" + UPDATED_USER_BILLING_STREET_2);
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingStreet2IsInShouldWork() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingStreet2 in DEFAULT_USER_BILLING_STREET_2 or UPDATED_USER_BILLING_STREET_2
        defaultUserBillingShouldBeFound("userBillingStreet2.in=" + DEFAULT_USER_BILLING_STREET_2 + "," + UPDATED_USER_BILLING_STREET_2);

        // Get all the userBillingList where userBillingStreet2 equals to UPDATED_USER_BILLING_STREET_2
        defaultUserBillingShouldNotBeFound("userBillingStreet2.in=" + UPDATED_USER_BILLING_STREET_2);
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingStreet2IsNullOrNotNull() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingStreet2 is not null
        defaultUserBillingShouldBeFound("userBillingStreet2.specified=true");

        // Get all the userBillingList where userBillingStreet2 is null
        defaultUserBillingShouldNotBeFound("userBillingStreet2.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingCityIsEqualToSomething() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingCity equals to DEFAULT_USER_BILLING_CITY
        defaultUserBillingShouldBeFound("userBillingCity.equals=" + DEFAULT_USER_BILLING_CITY);

        // Get all the userBillingList where userBillingCity equals to UPDATED_USER_BILLING_CITY
        defaultUserBillingShouldNotBeFound("userBillingCity.equals=" + UPDATED_USER_BILLING_CITY);
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingCityIsInShouldWork() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingCity in DEFAULT_USER_BILLING_CITY or UPDATED_USER_BILLING_CITY
        defaultUserBillingShouldBeFound("userBillingCity.in=" + DEFAULT_USER_BILLING_CITY + "," + UPDATED_USER_BILLING_CITY);

        // Get all the userBillingList where userBillingCity equals to UPDATED_USER_BILLING_CITY
        defaultUserBillingShouldNotBeFound("userBillingCity.in=" + UPDATED_USER_BILLING_CITY);
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingCity is not null
        defaultUserBillingShouldBeFound("userBillingCity.specified=true");

        // Get all the userBillingList where userBillingCity is null
        defaultUserBillingShouldNotBeFound("userBillingCity.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingStateIsEqualToSomething() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingState equals to DEFAULT_USER_BILLING_STATE
        defaultUserBillingShouldBeFound("userBillingState.equals=" + DEFAULT_USER_BILLING_STATE);

        // Get all the userBillingList where userBillingState equals to UPDATED_USER_BILLING_STATE
        defaultUserBillingShouldNotBeFound("userBillingState.equals=" + UPDATED_USER_BILLING_STATE);
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingStateIsInShouldWork() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingState in DEFAULT_USER_BILLING_STATE or UPDATED_USER_BILLING_STATE
        defaultUserBillingShouldBeFound("userBillingState.in=" + DEFAULT_USER_BILLING_STATE + "," + UPDATED_USER_BILLING_STATE);

        // Get all the userBillingList where userBillingState equals to UPDATED_USER_BILLING_STATE
        defaultUserBillingShouldNotBeFound("userBillingState.in=" + UPDATED_USER_BILLING_STATE);
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingState is not null
        defaultUserBillingShouldBeFound("userBillingState.specified=true");

        // Get all the userBillingList where userBillingState is null
        defaultUserBillingShouldNotBeFound("userBillingState.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingCountry equals to DEFAULT_USER_BILLING_COUNTRY
        defaultUserBillingShouldBeFound("userBillingCountry.equals=" + DEFAULT_USER_BILLING_COUNTRY);

        // Get all the userBillingList where userBillingCountry equals to UPDATED_USER_BILLING_COUNTRY
        defaultUserBillingShouldNotBeFound("userBillingCountry.equals=" + UPDATED_USER_BILLING_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingCountryIsInShouldWork() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingCountry in DEFAULT_USER_BILLING_COUNTRY or UPDATED_USER_BILLING_COUNTRY
        defaultUserBillingShouldBeFound("userBillingCountry.in=" + DEFAULT_USER_BILLING_COUNTRY + "," + UPDATED_USER_BILLING_COUNTRY);

        // Get all the userBillingList where userBillingCountry equals to UPDATED_USER_BILLING_COUNTRY
        defaultUserBillingShouldNotBeFound("userBillingCountry.in=" + UPDATED_USER_BILLING_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingCountry is not null
        defaultUserBillingShouldBeFound("userBillingCountry.specified=true");

        // Get all the userBillingList where userBillingCountry is null
        defaultUserBillingShouldNotBeFound("userBillingCountry.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingZipcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingZipcode equals to DEFAULT_USER_BILLING_ZIPCODE
        defaultUserBillingShouldBeFound("userBillingZipcode.equals=" + DEFAULT_USER_BILLING_ZIPCODE);

        // Get all the userBillingList where userBillingZipcode equals to UPDATED_USER_BILLING_ZIPCODE
        defaultUserBillingShouldNotBeFound("userBillingZipcode.equals=" + UPDATED_USER_BILLING_ZIPCODE);
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingZipcodeIsInShouldWork() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingZipcode in DEFAULT_USER_BILLING_ZIPCODE or UPDATED_USER_BILLING_ZIPCODE
        defaultUserBillingShouldBeFound("userBillingZipcode.in=" + DEFAULT_USER_BILLING_ZIPCODE + "," + UPDATED_USER_BILLING_ZIPCODE);

        // Get all the userBillingList where userBillingZipcode equals to UPDATED_USER_BILLING_ZIPCODE
        defaultUserBillingShouldNotBeFound("userBillingZipcode.in=" + UPDATED_USER_BILLING_ZIPCODE);
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserBillingZipcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBillingRepository.saveAndFlush(userBilling);

        // Get all the userBillingList where userBillingZipcode is not null
        defaultUserBillingShouldBeFound("userBillingZipcode.specified=true");

        // Get all the userBillingList where userBillingZipcode is null
        defaultUserBillingShouldNotBeFound("userBillingZipcode.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserBillingsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        userBilling.setUser(user);
        userBillingRepository.saveAndFlush(userBilling);
        Long userId = user.getId();

        // Get all the userBillingList where user equals to userId
        defaultUserBillingShouldBeFound("userId.equals=" + userId);

        // Get all the userBillingList where user equals to userId + 1
        defaultUserBillingShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultUserBillingShouldBeFound(String filter) throws Exception {
        restUserBillingMockMvc.perform(get("/api/user-billings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userBilling.getId().intValue())))
            .andExpect(jsonPath("$.[*].userBillingName").value(hasItem(DEFAULT_USER_BILLING_NAME.toString())))
            .andExpect(jsonPath("$.[*].userBillingStreet1").value(hasItem(DEFAULT_USER_BILLING_STREET_1.toString())))
            .andExpect(jsonPath("$.[*].userBillingStreet2").value(hasItem(DEFAULT_USER_BILLING_STREET_2.toString())))
            .andExpect(jsonPath("$.[*].userBillingCity").value(hasItem(DEFAULT_USER_BILLING_CITY.toString())))
            .andExpect(jsonPath("$.[*].userBillingState").value(hasItem(DEFAULT_USER_BILLING_STATE.toString())))
            .andExpect(jsonPath("$.[*].userBillingCountry").value(hasItem(DEFAULT_USER_BILLING_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].userBillingZipcode").value(hasItem(DEFAULT_USER_BILLING_ZIPCODE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultUserBillingShouldNotBeFound(String filter) throws Exception {
        restUserBillingMockMvc.perform(get("/api/user-billings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingUserBilling() throws Exception {
        // Get the userBilling
        restUserBillingMockMvc.perform(get("/api/user-billings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserBilling() throws Exception {
        // Initialize the database
        userBillingService.save(userBilling);

        int databaseSizeBeforeUpdate = userBillingRepository.findAll().size();

        // Update the userBilling
        UserBilling updatedUserBilling = userBillingRepository.findOne(userBilling.getId());
        // Disconnect from session so that the updates on updatedUserBilling are not directly saved in db
        em.detach(updatedUserBilling);
        updatedUserBilling
            .userBillingName(UPDATED_USER_BILLING_NAME)
            .userBillingStreet1(UPDATED_USER_BILLING_STREET_1)
            .userBillingStreet2(UPDATED_USER_BILLING_STREET_2)
            .userBillingCity(UPDATED_USER_BILLING_CITY)
            .userBillingState(UPDATED_USER_BILLING_STATE)
            .userBillingCountry(UPDATED_USER_BILLING_COUNTRY)
            .userBillingZipcode(UPDATED_USER_BILLING_ZIPCODE);

        restUserBillingMockMvc.perform(put("/api/user-billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserBilling)))
            .andExpect(status().isOk());

        // Validate the UserBilling in the database
        List<UserBilling> userBillingList = userBillingRepository.findAll();
        assertThat(userBillingList).hasSize(databaseSizeBeforeUpdate);
        UserBilling testUserBilling = userBillingList.get(userBillingList.size() - 1);
        assertThat(testUserBilling.getUserBillingName()).isEqualTo(UPDATED_USER_BILLING_NAME);
        assertThat(testUserBilling.getUserBillingStreet1()).isEqualTo(UPDATED_USER_BILLING_STREET_1);
        assertThat(testUserBilling.getUserBillingStreet2()).isEqualTo(UPDATED_USER_BILLING_STREET_2);
        assertThat(testUserBilling.getUserBillingCity()).isEqualTo(UPDATED_USER_BILLING_CITY);
        assertThat(testUserBilling.getUserBillingState()).isEqualTo(UPDATED_USER_BILLING_STATE);
        assertThat(testUserBilling.getUserBillingCountry()).isEqualTo(UPDATED_USER_BILLING_COUNTRY);
        assertThat(testUserBilling.getUserBillingZipcode()).isEqualTo(UPDATED_USER_BILLING_ZIPCODE);

        // Validate the UserBilling in Elasticsearch
        UserBilling userBillingEs = userBillingSearchRepository.findOne(testUserBilling.getId());
        assertThat(userBillingEs).isEqualToIgnoringGivenFields(testUserBilling);
    }

    @Test
    @Transactional
    public void updateNonExistingUserBilling() throws Exception {
        int databaseSizeBeforeUpdate = userBillingRepository.findAll().size();

        // Create the UserBilling

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserBillingMockMvc.perform(put("/api/user-billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBilling)))
            .andExpect(status().isCreated());

        // Validate the UserBilling in the database
        List<UserBilling> userBillingList = userBillingRepository.findAll();
        assertThat(userBillingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserBilling() throws Exception {
        // Initialize the database
        userBillingService.save(userBilling);

        int databaseSizeBeforeDelete = userBillingRepository.findAll().size();

        // Get the userBilling
        restUserBillingMockMvc.perform(delete("/api/user-billings/{id}", userBilling.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userBillingExistsInEs = userBillingSearchRepository.exists(userBilling.getId());
        assertThat(userBillingExistsInEs).isFalse();

        // Validate the database is empty
        List<UserBilling> userBillingList = userBillingRepository.findAll();
        assertThat(userBillingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserBilling() throws Exception {
        // Initialize the database
        userBillingService.save(userBilling);

        // Search the userBilling
        restUserBillingMockMvc.perform(get("/api/_search/user-billings?query=id:" + userBilling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userBilling.getId().intValue())))
            .andExpect(jsonPath("$.[*].userBillingName").value(hasItem(DEFAULT_USER_BILLING_NAME.toString())))
            .andExpect(jsonPath("$.[*].userBillingStreet1").value(hasItem(DEFAULT_USER_BILLING_STREET_1.toString())))
            .andExpect(jsonPath("$.[*].userBillingStreet2").value(hasItem(DEFAULT_USER_BILLING_STREET_2.toString())))
            .andExpect(jsonPath("$.[*].userBillingCity").value(hasItem(DEFAULT_USER_BILLING_CITY.toString())))
            .andExpect(jsonPath("$.[*].userBillingState").value(hasItem(DEFAULT_USER_BILLING_STATE.toString())))
            .andExpect(jsonPath("$.[*].userBillingCountry").value(hasItem(DEFAULT_USER_BILLING_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].userBillingZipcode").value(hasItem(DEFAULT_USER_BILLING_ZIPCODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserBilling.class);
        UserBilling userBilling1 = new UserBilling();
        userBilling1.setId(1L);
        UserBilling userBilling2 = new UserBilling();
        userBilling2.setId(userBilling1.getId());
        assertThat(userBilling1).isEqualTo(userBilling2);
        userBilling2.setId(2L);
        assertThat(userBilling1).isNotEqualTo(userBilling2);
        userBilling1.setId(null);
        assertThat(userBilling1).isNotEqualTo(userBilling2);
    }
}
