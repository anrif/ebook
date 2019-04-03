package com.acumen.article.web.rest;

import com.acumen.article.ArticleApp;

import com.acumen.article.domain.UserShipping;
import com.acumen.article.domain.User;
import com.acumen.article.repository.UserShippingRepository;
import com.acumen.article.service.UserShippingService;
import com.acumen.article.repository.search.UserShippingSearchRepository;
import com.acumen.article.web.rest.errors.ExceptionTranslator;
import com.acumen.article.service.dto.UserShippingCriteria;
import com.acumen.article.service.UserShippingQueryService;

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
 * Test class for the UserShippingResource REST controller.
 *
 * @see UserShippingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleApp.class)
public class UserShippingResourceIntTest {

    private static final String DEFAULT_USER_SHIPPING_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_SHIPPING_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_SHIPPING_STREET_1 = "AAAAAAAAAA";
    private static final String UPDATED_USER_SHIPPING_STREET_1 = "BBBBBBBBBB";

    private static final String DEFAULT_USER_SHIPPING_STREET_2 = "AAAAAAAAAA";
    private static final String UPDATED_USER_SHIPPING_STREET_2 = "BBBBBBBBBB";

    private static final String DEFAULT_USER_SHIPPING_CITY = "AAAAAAAAAA";
    private static final String UPDATED_USER_SHIPPING_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_USER_SHIPPING_STATE = "AAAAAAAAAA";
    private static final String UPDATED_USER_SHIPPING_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_USER_SHIPPING_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_USER_SHIPPING_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_USER_SHIPPING_ZIPCODE = "AAAAAAAAAA";
    private static final String UPDATED_USER_SHIPPING_ZIPCODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USER_SHIPPING_DEFAULT = false;
    private static final Boolean UPDATED_USER_SHIPPING_DEFAULT = true;

    @Autowired
    private UserShippingRepository userShippingRepository;

    @Autowired
    private UserShippingService userShippingService;

    @Autowired
    private UserShippingSearchRepository userShippingSearchRepository;

    @Autowired
    private UserShippingQueryService userShippingQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserShippingMockMvc;

    private UserShipping userShipping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserShippingResource userShippingResource = new UserShippingResource(userShippingService, userShippingQueryService);
        this.restUserShippingMockMvc = MockMvcBuilders.standaloneSetup(userShippingResource)
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
    public static UserShipping createEntity(EntityManager em) {
        UserShipping userShipping = new UserShipping()
            .userShippingName(DEFAULT_USER_SHIPPING_NAME)
            .userShippingStreet1(DEFAULT_USER_SHIPPING_STREET_1)
            .userShippingStreet2(DEFAULT_USER_SHIPPING_STREET_2)
            .userShippingCity(DEFAULT_USER_SHIPPING_CITY)
            .userShippingState(DEFAULT_USER_SHIPPING_STATE)
            .userShippingCountry(DEFAULT_USER_SHIPPING_COUNTRY)
            .userShippingZipcode(DEFAULT_USER_SHIPPING_ZIPCODE)
            .userShippingDefault(DEFAULT_USER_SHIPPING_DEFAULT);
        return userShipping;
    }

    @Before
    public void initTest() {
        userShippingSearchRepository.deleteAll();
        userShipping = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserShipping() throws Exception {
        int databaseSizeBeforeCreate = userShippingRepository.findAll().size();

        // Create the UserShipping
        restUserShippingMockMvc.perform(post("/api/user-shippings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userShipping)))
            .andExpect(status().isCreated());

        // Validate the UserShipping in the database
        List<UserShipping> userShippingList = userShippingRepository.findAll();
        assertThat(userShippingList).hasSize(databaseSizeBeforeCreate + 1);
        UserShipping testUserShipping = userShippingList.get(userShippingList.size() - 1);
        assertThat(testUserShipping.getUserShippingName()).isEqualTo(DEFAULT_USER_SHIPPING_NAME);
        assertThat(testUserShipping.getUserShippingStreet1()).isEqualTo(DEFAULT_USER_SHIPPING_STREET_1);
        assertThat(testUserShipping.getUserShippingStreet2()).isEqualTo(DEFAULT_USER_SHIPPING_STREET_2);
        assertThat(testUserShipping.getUserShippingCity()).isEqualTo(DEFAULT_USER_SHIPPING_CITY);
        assertThat(testUserShipping.getUserShippingState()).isEqualTo(DEFAULT_USER_SHIPPING_STATE);
        assertThat(testUserShipping.getUserShippingCountry()).isEqualTo(DEFAULT_USER_SHIPPING_COUNTRY);
        assertThat(testUserShipping.getUserShippingZipcode()).isEqualTo(DEFAULT_USER_SHIPPING_ZIPCODE);
        assertThat(testUserShipping.isUserShippingDefault()).isEqualTo(DEFAULT_USER_SHIPPING_DEFAULT);

        // Validate the UserShipping in Elasticsearch
        UserShipping userShippingEs = userShippingSearchRepository.findOne(testUserShipping.getId());
        assertThat(userShippingEs).isEqualToIgnoringGivenFields(testUserShipping);
    }

    @Test
    @Transactional
    public void createUserShippingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userShippingRepository.findAll().size();

        // Create the UserShipping with an existing ID
        userShipping.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserShippingMockMvc.perform(post("/api/user-shippings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userShipping)))
            .andExpect(status().isBadRequest());

        // Validate the UserShipping in the database
        List<UserShipping> userShippingList = userShippingRepository.findAll();
        assertThat(userShippingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserShippingNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userShippingRepository.findAll().size();
        // set the field null
        userShipping.setUserShippingName(null);

        // Create the UserShipping, which fails.

        restUserShippingMockMvc.perform(post("/api/user-shippings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userShipping)))
            .andExpect(status().isBadRequest());

        List<UserShipping> userShippingList = userShippingRepository.findAll();
        assertThat(userShippingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserShippingCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = userShippingRepository.findAll().size();
        // set the field null
        userShipping.setUserShippingCity(null);

        // Create the UserShipping, which fails.

        restUserShippingMockMvc.perform(post("/api/user-shippings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userShipping)))
            .andExpect(status().isBadRequest());

        List<UserShipping> userShippingList = userShippingRepository.findAll();
        assertThat(userShippingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserShippingStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userShippingRepository.findAll().size();
        // set the field null
        userShipping.setUserShippingState(null);

        // Create the UserShipping, which fails.

        restUserShippingMockMvc.perform(post("/api/user-shippings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userShipping)))
            .andExpect(status().isBadRequest());

        List<UserShipping> userShippingList = userShippingRepository.findAll();
        assertThat(userShippingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserShippingCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = userShippingRepository.findAll().size();
        // set the field null
        userShipping.setUserShippingCountry(null);

        // Create the UserShipping, which fails.

        restUserShippingMockMvc.perform(post("/api/user-shippings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userShipping)))
            .andExpect(status().isBadRequest());

        List<UserShipping> userShippingList = userShippingRepository.findAll();
        assertThat(userShippingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserShippingZipcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userShippingRepository.findAll().size();
        // set the field null
        userShipping.setUserShippingZipcode(null);

        // Create the UserShipping, which fails.

        restUserShippingMockMvc.perform(post("/api/user-shippings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userShipping)))
            .andExpect(status().isBadRequest());

        List<UserShipping> userShippingList = userShippingRepository.findAll();
        assertThat(userShippingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserShippings() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList
        restUserShippingMockMvc.perform(get("/api/user-shippings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userShipping.getId().intValue())))
            .andExpect(jsonPath("$.[*].userShippingName").value(hasItem(DEFAULT_USER_SHIPPING_NAME.toString())))
            .andExpect(jsonPath("$.[*].userShippingStreet1").value(hasItem(DEFAULT_USER_SHIPPING_STREET_1.toString())))
            .andExpect(jsonPath("$.[*].userShippingStreet2").value(hasItem(DEFAULT_USER_SHIPPING_STREET_2.toString())))
            .andExpect(jsonPath("$.[*].userShippingCity").value(hasItem(DEFAULT_USER_SHIPPING_CITY.toString())))
            .andExpect(jsonPath("$.[*].userShippingState").value(hasItem(DEFAULT_USER_SHIPPING_STATE.toString())))
            .andExpect(jsonPath("$.[*].userShippingCountry").value(hasItem(DEFAULT_USER_SHIPPING_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].userShippingZipcode").value(hasItem(DEFAULT_USER_SHIPPING_ZIPCODE.toString())))
            .andExpect(jsonPath("$.[*].userShippingDefault").value(hasItem(DEFAULT_USER_SHIPPING_DEFAULT.booleanValue())));
    }

    @Test
    @Transactional
    public void getUserShipping() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get the userShipping
        restUserShippingMockMvc.perform(get("/api/user-shippings/{id}", userShipping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userShipping.getId().intValue()))
            .andExpect(jsonPath("$.userShippingName").value(DEFAULT_USER_SHIPPING_NAME.toString()))
            .andExpect(jsonPath("$.userShippingStreet1").value(DEFAULT_USER_SHIPPING_STREET_1.toString()))
            .andExpect(jsonPath("$.userShippingStreet2").value(DEFAULT_USER_SHIPPING_STREET_2.toString()))
            .andExpect(jsonPath("$.userShippingCity").value(DEFAULT_USER_SHIPPING_CITY.toString()))
            .andExpect(jsonPath("$.userShippingState").value(DEFAULT_USER_SHIPPING_STATE.toString()))
            .andExpect(jsonPath("$.userShippingCountry").value(DEFAULT_USER_SHIPPING_COUNTRY.toString()))
            .andExpect(jsonPath("$.userShippingZipcode").value(DEFAULT_USER_SHIPPING_ZIPCODE.toString()))
            .andExpect(jsonPath("$.userShippingDefault").value(DEFAULT_USER_SHIPPING_DEFAULT.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingName equals to DEFAULT_USER_SHIPPING_NAME
        defaultUserShippingShouldBeFound("userShippingName.equals=" + DEFAULT_USER_SHIPPING_NAME);

        // Get all the userShippingList where userShippingName equals to UPDATED_USER_SHIPPING_NAME
        defaultUserShippingShouldNotBeFound("userShippingName.equals=" + UPDATED_USER_SHIPPING_NAME);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingNameIsInShouldWork() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingName in DEFAULT_USER_SHIPPING_NAME or UPDATED_USER_SHIPPING_NAME
        defaultUserShippingShouldBeFound("userShippingName.in=" + DEFAULT_USER_SHIPPING_NAME + "," + UPDATED_USER_SHIPPING_NAME);

        // Get all the userShippingList where userShippingName equals to UPDATED_USER_SHIPPING_NAME
        defaultUserShippingShouldNotBeFound("userShippingName.in=" + UPDATED_USER_SHIPPING_NAME);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingName is not null
        defaultUserShippingShouldBeFound("userShippingName.specified=true");

        // Get all the userShippingList where userShippingName is null
        defaultUserShippingShouldNotBeFound("userShippingName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingStreet1IsEqualToSomething() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingStreet1 equals to DEFAULT_USER_SHIPPING_STREET_1
        defaultUserShippingShouldBeFound("userShippingStreet1.equals=" + DEFAULT_USER_SHIPPING_STREET_1);

        // Get all the userShippingList where userShippingStreet1 equals to UPDATED_USER_SHIPPING_STREET_1
        defaultUserShippingShouldNotBeFound("userShippingStreet1.equals=" + UPDATED_USER_SHIPPING_STREET_1);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingStreet1IsInShouldWork() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingStreet1 in DEFAULT_USER_SHIPPING_STREET_1 or UPDATED_USER_SHIPPING_STREET_1
        defaultUserShippingShouldBeFound("userShippingStreet1.in=" + DEFAULT_USER_SHIPPING_STREET_1 + "," + UPDATED_USER_SHIPPING_STREET_1);

        // Get all the userShippingList where userShippingStreet1 equals to UPDATED_USER_SHIPPING_STREET_1
        defaultUserShippingShouldNotBeFound("userShippingStreet1.in=" + UPDATED_USER_SHIPPING_STREET_1);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingStreet1IsNullOrNotNull() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingStreet1 is not null
        defaultUserShippingShouldBeFound("userShippingStreet1.specified=true");

        // Get all the userShippingList where userShippingStreet1 is null
        defaultUserShippingShouldNotBeFound("userShippingStreet1.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingStreet2IsEqualToSomething() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingStreet2 equals to DEFAULT_USER_SHIPPING_STREET_2
        defaultUserShippingShouldBeFound("userShippingStreet2.equals=" + DEFAULT_USER_SHIPPING_STREET_2);

        // Get all the userShippingList where userShippingStreet2 equals to UPDATED_USER_SHIPPING_STREET_2
        defaultUserShippingShouldNotBeFound("userShippingStreet2.equals=" + UPDATED_USER_SHIPPING_STREET_2);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingStreet2IsInShouldWork() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingStreet2 in DEFAULT_USER_SHIPPING_STREET_2 or UPDATED_USER_SHIPPING_STREET_2
        defaultUserShippingShouldBeFound("userShippingStreet2.in=" + DEFAULT_USER_SHIPPING_STREET_2 + "," + UPDATED_USER_SHIPPING_STREET_2);

        // Get all the userShippingList where userShippingStreet2 equals to UPDATED_USER_SHIPPING_STREET_2
        defaultUserShippingShouldNotBeFound("userShippingStreet2.in=" + UPDATED_USER_SHIPPING_STREET_2);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingStreet2IsNullOrNotNull() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingStreet2 is not null
        defaultUserShippingShouldBeFound("userShippingStreet2.specified=true");

        // Get all the userShippingList where userShippingStreet2 is null
        defaultUserShippingShouldNotBeFound("userShippingStreet2.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingCityIsEqualToSomething() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingCity equals to DEFAULT_USER_SHIPPING_CITY
        defaultUserShippingShouldBeFound("userShippingCity.equals=" + DEFAULT_USER_SHIPPING_CITY);

        // Get all the userShippingList where userShippingCity equals to UPDATED_USER_SHIPPING_CITY
        defaultUserShippingShouldNotBeFound("userShippingCity.equals=" + UPDATED_USER_SHIPPING_CITY);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingCityIsInShouldWork() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingCity in DEFAULT_USER_SHIPPING_CITY or UPDATED_USER_SHIPPING_CITY
        defaultUserShippingShouldBeFound("userShippingCity.in=" + DEFAULT_USER_SHIPPING_CITY + "," + UPDATED_USER_SHIPPING_CITY);

        // Get all the userShippingList where userShippingCity equals to UPDATED_USER_SHIPPING_CITY
        defaultUserShippingShouldNotBeFound("userShippingCity.in=" + UPDATED_USER_SHIPPING_CITY);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingCity is not null
        defaultUserShippingShouldBeFound("userShippingCity.specified=true");

        // Get all the userShippingList where userShippingCity is null
        defaultUserShippingShouldNotBeFound("userShippingCity.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingStateIsEqualToSomething() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingState equals to DEFAULT_USER_SHIPPING_STATE
        defaultUserShippingShouldBeFound("userShippingState.equals=" + DEFAULT_USER_SHIPPING_STATE);

        // Get all the userShippingList where userShippingState equals to UPDATED_USER_SHIPPING_STATE
        defaultUserShippingShouldNotBeFound("userShippingState.equals=" + UPDATED_USER_SHIPPING_STATE);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingStateIsInShouldWork() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingState in DEFAULT_USER_SHIPPING_STATE or UPDATED_USER_SHIPPING_STATE
        defaultUserShippingShouldBeFound("userShippingState.in=" + DEFAULT_USER_SHIPPING_STATE + "," + UPDATED_USER_SHIPPING_STATE);

        // Get all the userShippingList where userShippingState equals to UPDATED_USER_SHIPPING_STATE
        defaultUserShippingShouldNotBeFound("userShippingState.in=" + UPDATED_USER_SHIPPING_STATE);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingState is not null
        defaultUserShippingShouldBeFound("userShippingState.specified=true");

        // Get all the userShippingList where userShippingState is null
        defaultUserShippingShouldNotBeFound("userShippingState.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingCountry equals to DEFAULT_USER_SHIPPING_COUNTRY
        defaultUserShippingShouldBeFound("userShippingCountry.equals=" + DEFAULT_USER_SHIPPING_COUNTRY);

        // Get all the userShippingList where userShippingCountry equals to UPDATED_USER_SHIPPING_COUNTRY
        defaultUserShippingShouldNotBeFound("userShippingCountry.equals=" + UPDATED_USER_SHIPPING_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingCountryIsInShouldWork() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingCountry in DEFAULT_USER_SHIPPING_COUNTRY or UPDATED_USER_SHIPPING_COUNTRY
        defaultUserShippingShouldBeFound("userShippingCountry.in=" + DEFAULT_USER_SHIPPING_COUNTRY + "," + UPDATED_USER_SHIPPING_COUNTRY);

        // Get all the userShippingList where userShippingCountry equals to UPDATED_USER_SHIPPING_COUNTRY
        defaultUserShippingShouldNotBeFound("userShippingCountry.in=" + UPDATED_USER_SHIPPING_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingCountry is not null
        defaultUserShippingShouldBeFound("userShippingCountry.specified=true");

        // Get all the userShippingList where userShippingCountry is null
        defaultUserShippingShouldNotBeFound("userShippingCountry.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingZipcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingZipcode equals to DEFAULT_USER_SHIPPING_ZIPCODE
        defaultUserShippingShouldBeFound("userShippingZipcode.equals=" + DEFAULT_USER_SHIPPING_ZIPCODE);

        // Get all the userShippingList where userShippingZipcode equals to UPDATED_USER_SHIPPING_ZIPCODE
        defaultUserShippingShouldNotBeFound("userShippingZipcode.equals=" + UPDATED_USER_SHIPPING_ZIPCODE);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingZipcodeIsInShouldWork() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingZipcode in DEFAULT_USER_SHIPPING_ZIPCODE or UPDATED_USER_SHIPPING_ZIPCODE
        defaultUserShippingShouldBeFound("userShippingZipcode.in=" + DEFAULT_USER_SHIPPING_ZIPCODE + "," + UPDATED_USER_SHIPPING_ZIPCODE);

        // Get all the userShippingList where userShippingZipcode equals to UPDATED_USER_SHIPPING_ZIPCODE
        defaultUserShippingShouldNotBeFound("userShippingZipcode.in=" + UPDATED_USER_SHIPPING_ZIPCODE);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingZipcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingZipcode is not null
        defaultUserShippingShouldBeFound("userShippingZipcode.specified=true");

        // Get all the userShippingList where userShippingZipcode is null
        defaultUserShippingShouldNotBeFound("userShippingZipcode.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingDefaultIsEqualToSomething() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingDefault equals to DEFAULT_USER_SHIPPING_DEFAULT
        defaultUserShippingShouldBeFound("userShippingDefault.equals=" + DEFAULT_USER_SHIPPING_DEFAULT);

        // Get all the userShippingList where userShippingDefault equals to UPDATED_USER_SHIPPING_DEFAULT
        defaultUserShippingShouldNotBeFound("userShippingDefault.equals=" + UPDATED_USER_SHIPPING_DEFAULT);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingDefaultIsInShouldWork() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingDefault in DEFAULT_USER_SHIPPING_DEFAULT or UPDATED_USER_SHIPPING_DEFAULT
        defaultUserShippingShouldBeFound("userShippingDefault.in=" + DEFAULT_USER_SHIPPING_DEFAULT + "," + UPDATED_USER_SHIPPING_DEFAULT);

        // Get all the userShippingList where userShippingDefault equals to UPDATED_USER_SHIPPING_DEFAULT
        defaultUserShippingShouldNotBeFound("userShippingDefault.in=" + UPDATED_USER_SHIPPING_DEFAULT);
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserShippingDefaultIsNullOrNotNull() throws Exception {
        // Initialize the database
        userShippingRepository.saveAndFlush(userShipping);

        // Get all the userShippingList where userShippingDefault is not null
        defaultUserShippingShouldBeFound("userShippingDefault.specified=true");

        // Get all the userShippingList where userShippingDefault is null
        defaultUserShippingShouldNotBeFound("userShippingDefault.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserShippingsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        userShipping.setUser(user);
        userShippingRepository.saveAndFlush(userShipping);
        Long userId = user.getId();

        // Get all the userShippingList where user equals to userId
        defaultUserShippingShouldBeFound("userId.equals=" + userId);

        // Get all the userShippingList where user equals to userId + 1
        defaultUserShippingShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultUserShippingShouldBeFound(String filter) throws Exception {
        restUserShippingMockMvc.perform(get("/api/user-shippings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userShipping.getId().intValue())))
            .andExpect(jsonPath("$.[*].userShippingName").value(hasItem(DEFAULT_USER_SHIPPING_NAME.toString())))
            .andExpect(jsonPath("$.[*].userShippingStreet1").value(hasItem(DEFAULT_USER_SHIPPING_STREET_1.toString())))
            .andExpect(jsonPath("$.[*].userShippingStreet2").value(hasItem(DEFAULT_USER_SHIPPING_STREET_2.toString())))
            .andExpect(jsonPath("$.[*].userShippingCity").value(hasItem(DEFAULT_USER_SHIPPING_CITY.toString())))
            .andExpect(jsonPath("$.[*].userShippingState").value(hasItem(DEFAULT_USER_SHIPPING_STATE.toString())))
            .andExpect(jsonPath("$.[*].userShippingCountry").value(hasItem(DEFAULT_USER_SHIPPING_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].userShippingZipcode").value(hasItem(DEFAULT_USER_SHIPPING_ZIPCODE.toString())))
            .andExpect(jsonPath("$.[*].userShippingDefault").value(hasItem(DEFAULT_USER_SHIPPING_DEFAULT.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultUserShippingShouldNotBeFound(String filter) throws Exception {
        restUserShippingMockMvc.perform(get("/api/user-shippings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingUserShipping() throws Exception {
        // Get the userShipping
        restUserShippingMockMvc.perform(get("/api/user-shippings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserShipping() throws Exception {
        // Initialize the database
        userShippingService.save(userShipping);

        int databaseSizeBeforeUpdate = userShippingRepository.findAll().size();

        // Update the userShipping
        UserShipping updatedUserShipping = userShippingRepository.findOne(userShipping.getId());
        // Disconnect from session so that the updates on updatedUserShipping are not directly saved in db
        em.detach(updatedUserShipping);
        updatedUserShipping
            .userShippingName(UPDATED_USER_SHIPPING_NAME)
            .userShippingStreet1(UPDATED_USER_SHIPPING_STREET_1)
            .userShippingStreet2(UPDATED_USER_SHIPPING_STREET_2)
            .userShippingCity(UPDATED_USER_SHIPPING_CITY)
            .userShippingState(UPDATED_USER_SHIPPING_STATE)
            .userShippingCountry(UPDATED_USER_SHIPPING_COUNTRY)
            .userShippingZipcode(UPDATED_USER_SHIPPING_ZIPCODE)
            .userShippingDefault(UPDATED_USER_SHIPPING_DEFAULT);

        restUserShippingMockMvc.perform(put("/api/user-shippings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserShipping)))
            .andExpect(status().isOk());

        // Validate the UserShipping in the database
        List<UserShipping> userShippingList = userShippingRepository.findAll();
        assertThat(userShippingList).hasSize(databaseSizeBeforeUpdate);
        UserShipping testUserShipping = userShippingList.get(userShippingList.size() - 1);
        assertThat(testUserShipping.getUserShippingName()).isEqualTo(UPDATED_USER_SHIPPING_NAME);
        assertThat(testUserShipping.getUserShippingStreet1()).isEqualTo(UPDATED_USER_SHIPPING_STREET_1);
        assertThat(testUserShipping.getUserShippingStreet2()).isEqualTo(UPDATED_USER_SHIPPING_STREET_2);
        assertThat(testUserShipping.getUserShippingCity()).isEqualTo(UPDATED_USER_SHIPPING_CITY);
        assertThat(testUserShipping.getUserShippingState()).isEqualTo(UPDATED_USER_SHIPPING_STATE);
        assertThat(testUserShipping.getUserShippingCountry()).isEqualTo(UPDATED_USER_SHIPPING_COUNTRY);
        assertThat(testUserShipping.getUserShippingZipcode()).isEqualTo(UPDATED_USER_SHIPPING_ZIPCODE);
        assertThat(testUserShipping.isUserShippingDefault()).isEqualTo(UPDATED_USER_SHIPPING_DEFAULT);

        // Validate the UserShipping in Elasticsearch
        UserShipping userShippingEs = userShippingSearchRepository.findOne(testUserShipping.getId());
        assertThat(userShippingEs).isEqualToIgnoringGivenFields(testUserShipping);
    }

    @Test
    @Transactional
    public void updateNonExistingUserShipping() throws Exception {
        int databaseSizeBeforeUpdate = userShippingRepository.findAll().size();

        // Create the UserShipping

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserShippingMockMvc.perform(put("/api/user-shippings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userShipping)))
            .andExpect(status().isCreated());

        // Validate the UserShipping in the database
        List<UserShipping> userShippingList = userShippingRepository.findAll();
        assertThat(userShippingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserShipping() throws Exception {
        // Initialize the database
        userShippingService.save(userShipping);

        int databaseSizeBeforeDelete = userShippingRepository.findAll().size();

        // Get the userShipping
        restUserShippingMockMvc.perform(delete("/api/user-shippings/{id}", userShipping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userShippingExistsInEs = userShippingSearchRepository.exists(userShipping.getId());
        assertThat(userShippingExistsInEs).isFalse();

        // Validate the database is empty
        List<UserShipping> userShippingList = userShippingRepository.findAll();
        assertThat(userShippingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserShipping() throws Exception {
        // Initialize the database
        userShippingService.save(userShipping);

        // Search the userShipping
        restUserShippingMockMvc.perform(get("/api/_search/user-shippings?query=id:" + userShipping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userShipping.getId().intValue())))
            .andExpect(jsonPath("$.[*].userShippingName").value(hasItem(DEFAULT_USER_SHIPPING_NAME.toString())))
            .andExpect(jsonPath("$.[*].userShippingStreet1").value(hasItem(DEFAULT_USER_SHIPPING_STREET_1.toString())))
            .andExpect(jsonPath("$.[*].userShippingStreet2").value(hasItem(DEFAULT_USER_SHIPPING_STREET_2.toString())))
            .andExpect(jsonPath("$.[*].userShippingCity").value(hasItem(DEFAULT_USER_SHIPPING_CITY.toString())))
            .andExpect(jsonPath("$.[*].userShippingState").value(hasItem(DEFAULT_USER_SHIPPING_STATE.toString())))
            .andExpect(jsonPath("$.[*].userShippingCountry").value(hasItem(DEFAULT_USER_SHIPPING_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].userShippingZipcode").value(hasItem(DEFAULT_USER_SHIPPING_ZIPCODE.toString())))
            .andExpect(jsonPath("$.[*].userShippingDefault").value(hasItem(DEFAULT_USER_SHIPPING_DEFAULT.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserShipping.class);
        UserShipping userShipping1 = new UserShipping();
        userShipping1.setId(1L);
        UserShipping userShipping2 = new UserShipping();
        userShipping2.setId(userShipping1.getId());
        assertThat(userShipping1).isEqualTo(userShipping2);
        userShipping2.setId(2L);
        assertThat(userShipping1).isNotEqualTo(userShipping2);
        userShipping1.setId(null);
        assertThat(userShipping1).isNotEqualTo(userShipping2);
    }
}
