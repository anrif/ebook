package com.acumen.article.web.rest;

import com.acumen.article.ArticleApp;

import com.acumen.article.domain.ShippingAddress;
import com.acumen.article.domain.Ordered;
import com.acumen.article.repository.ShippingAddressRepository;
import com.acumen.article.service.ShippingAddressService;
import com.acumen.article.repository.search.ShippingAddressSearchRepository;
import com.acumen.article.web.rest.errors.ExceptionTranslator;
import com.acumen.article.service.dto.ShippingAddressCriteria;
import com.acumen.article.service.ShippingAddressQueryService;

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
 * Test class for the ShippingAddressResource REST controller.
 *
 * @see ShippingAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleApp.class)
public class ShippingAddressResourceIntTest {

    private static final String DEFAULT_SHIPPING_ADDRESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_ADDRESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_ADDRESS_STREET_1 = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_ADDRESS_STREET_1 = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_ADDRESS_STREET_2 = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_ADDRESS_STREET_2 = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_ADDRESS_CITY = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_ADDRESS_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_ADDRESS_STATE = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_ADDRESS_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_ADDRESS_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_ADDRESS_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_ADDRESS_ZIPCODE = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_ADDRESS_ZIPCODE = "BBBBBBBBBB";

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private ShippingAddressService shippingAddressService;

    @Autowired
    private ShippingAddressSearchRepository shippingAddressSearchRepository;

    @Autowired
    private ShippingAddressQueryService shippingAddressQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShippingAddressMockMvc;

    private ShippingAddress shippingAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShippingAddressResource shippingAddressResource = new ShippingAddressResource(shippingAddressService, shippingAddressQueryService);
        this.restShippingAddressMockMvc = MockMvcBuilders.standaloneSetup(shippingAddressResource)
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
    public static ShippingAddress createEntity(EntityManager em) {
        ShippingAddress shippingAddress = new ShippingAddress()
            .shippingAddressName(DEFAULT_SHIPPING_ADDRESS_NAME)
            .shippingAddressStreet1(DEFAULT_SHIPPING_ADDRESS_STREET_1)
            .shippingAddressStreet2(DEFAULT_SHIPPING_ADDRESS_STREET_2)
            .shippingAddressCity(DEFAULT_SHIPPING_ADDRESS_CITY)
            .shippingAddressState(DEFAULT_SHIPPING_ADDRESS_STATE)
            .shippingAddressCountry(DEFAULT_SHIPPING_ADDRESS_COUNTRY)
            .shippingAddressZipcode(DEFAULT_SHIPPING_ADDRESS_ZIPCODE);
        return shippingAddress;
    }

    @Before
    public void initTest() {
        shippingAddressSearchRepository.deleteAll();
        shippingAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createShippingAddress() throws Exception {
        int databaseSizeBeforeCreate = shippingAddressRepository.findAll().size();

        // Create the ShippingAddress
        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddress)))
            .andExpect(status().isCreated());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeCreate + 1);
        ShippingAddress testShippingAddress = shippingAddressList.get(shippingAddressList.size() - 1);
        assertThat(testShippingAddress.getShippingAddressName()).isEqualTo(DEFAULT_SHIPPING_ADDRESS_NAME);
        assertThat(testShippingAddress.getShippingAddressStreet1()).isEqualTo(DEFAULT_SHIPPING_ADDRESS_STREET_1);
        assertThat(testShippingAddress.getShippingAddressStreet2()).isEqualTo(DEFAULT_SHIPPING_ADDRESS_STREET_2);
        assertThat(testShippingAddress.getShippingAddressCity()).isEqualTo(DEFAULT_SHIPPING_ADDRESS_CITY);
        assertThat(testShippingAddress.getShippingAddressState()).isEqualTo(DEFAULT_SHIPPING_ADDRESS_STATE);
        assertThat(testShippingAddress.getShippingAddressCountry()).isEqualTo(DEFAULT_SHIPPING_ADDRESS_COUNTRY);
        assertThat(testShippingAddress.getShippingAddressZipcode()).isEqualTo(DEFAULT_SHIPPING_ADDRESS_ZIPCODE);

        // Validate the ShippingAddress in Elasticsearch
        ShippingAddress shippingAddressEs = shippingAddressSearchRepository.findOne(testShippingAddress.getId());
        assertThat(shippingAddressEs).isEqualToIgnoringGivenFields(testShippingAddress);
    }

    @Test
    @Transactional
    public void createShippingAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shippingAddressRepository.findAll().size();

        // Create the ShippingAddress with an existing ID
        shippingAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddress)))
            .andExpect(status().isBadRequest());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkShippingAddressNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingAddressRepository.findAll().size();
        // set the field null
        shippingAddress.setShippingAddressName(null);

        // Create the ShippingAddress, which fails.

        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddress)))
            .andExpect(status().isBadRequest());

        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShippingAddressCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingAddressRepository.findAll().size();
        // set the field null
        shippingAddress.setShippingAddressCity(null);

        // Create the ShippingAddress, which fails.

        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddress)))
            .andExpect(status().isBadRequest());

        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShippingAddressStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingAddressRepository.findAll().size();
        // set the field null
        shippingAddress.setShippingAddressState(null);

        // Create the ShippingAddress, which fails.

        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddress)))
            .andExpect(status().isBadRequest());

        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShippingAddressCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingAddressRepository.findAll().size();
        // set the field null
        shippingAddress.setShippingAddressCountry(null);

        // Create the ShippingAddress, which fails.

        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddress)))
            .andExpect(status().isBadRequest());

        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShippingAddresses() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].shippingAddressName").value(hasItem(DEFAULT_SHIPPING_ADDRESS_NAME.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressStreet1").value(hasItem(DEFAULT_SHIPPING_ADDRESS_STREET_1.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressStreet2").value(hasItem(DEFAULT_SHIPPING_ADDRESS_STREET_2.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressCity").value(hasItem(DEFAULT_SHIPPING_ADDRESS_CITY.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressState").value(hasItem(DEFAULT_SHIPPING_ADDRESS_STATE.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressCountry").value(hasItem(DEFAULT_SHIPPING_ADDRESS_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressZipcode").value(hasItem(DEFAULT_SHIPPING_ADDRESS_ZIPCODE.toString())));
    }

    @Test
    @Transactional
    public void getShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get the shippingAddress
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses/{id}", shippingAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shippingAddress.getId().intValue()))
            .andExpect(jsonPath("$.shippingAddressName").value(DEFAULT_SHIPPING_ADDRESS_NAME.toString()))
            .andExpect(jsonPath("$.shippingAddressStreet1").value(DEFAULT_SHIPPING_ADDRESS_STREET_1.toString()))
            .andExpect(jsonPath("$.shippingAddressStreet2").value(DEFAULT_SHIPPING_ADDRESS_STREET_2.toString()))
            .andExpect(jsonPath("$.shippingAddressCity").value(DEFAULT_SHIPPING_ADDRESS_CITY.toString()))
            .andExpect(jsonPath("$.shippingAddressState").value(DEFAULT_SHIPPING_ADDRESS_STATE.toString()))
            .andExpect(jsonPath("$.shippingAddressCountry").value(DEFAULT_SHIPPING_ADDRESS_COUNTRY.toString()))
            .andExpect(jsonPath("$.shippingAddressZipcode").value(DEFAULT_SHIPPING_ADDRESS_ZIPCODE.toString()));
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressNameIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressName equals to DEFAULT_SHIPPING_ADDRESS_NAME
        defaultShippingAddressShouldBeFound("shippingAddressName.equals=" + DEFAULT_SHIPPING_ADDRESS_NAME);

        // Get all the shippingAddressList where shippingAddressName equals to UPDATED_SHIPPING_ADDRESS_NAME
        defaultShippingAddressShouldNotBeFound("shippingAddressName.equals=" + UPDATED_SHIPPING_ADDRESS_NAME);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressNameIsInShouldWork() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressName in DEFAULT_SHIPPING_ADDRESS_NAME or UPDATED_SHIPPING_ADDRESS_NAME
        defaultShippingAddressShouldBeFound("shippingAddressName.in=" + DEFAULT_SHIPPING_ADDRESS_NAME + "," + UPDATED_SHIPPING_ADDRESS_NAME);

        // Get all the shippingAddressList where shippingAddressName equals to UPDATED_SHIPPING_ADDRESS_NAME
        defaultShippingAddressShouldNotBeFound("shippingAddressName.in=" + UPDATED_SHIPPING_ADDRESS_NAME);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressName is not null
        defaultShippingAddressShouldBeFound("shippingAddressName.specified=true");

        // Get all the shippingAddressList where shippingAddressName is null
        defaultShippingAddressShouldNotBeFound("shippingAddressName.specified=false");
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressStreet1IsEqualToSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressStreet1 equals to DEFAULT_SHIPPING_ADDRESS_STREET_1
        defaultShippingAddressShouldBeFound("shippingAddressStreet1.equals=" + DEFAULT_SHIPPING_ADDRESS_STREET_1);

        // Get all the shippingAddressList where shippingAddressStreet1 equals to UPDATED_SHIPPING_ADDRESS_STREET_1
        defaultShippingAddressShouldNotBeFound("shippingAddressStreet1.equals=" + UPDATED_SHIPPING_ADDRESS_STREET_1);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressStreet1IsInShouldWork() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressStreet1 in DEFAULT_SHIPPING_ADDRESS_STREET_1 or UPDATED_SHIPPING_ADDRESS_STREET_1
        defaultShippingAddressShouldBeFound("shippingAddressStreet1.in=" + DEFAULT_SHIPPING_ADDRESS_STREET_1 + "," + UPDATED_SHIPPING_ADDRESS_STREET_1);

        // Get all the shippingAddressList where shippingAddressStreet1 equals to UPDATED_SHIPPING_ADDRESS_STREET_1
        defaultShippingAddressShouldNotBeFound("shippingAddressStreet1.in=" + UPDATED_SHIPPING_ADDRESS_STREET_1);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressStreet1IsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressStreet1 is not null
        defaultShippingAddressShouldBeFound("shippingAddressStreet1.specified=true");

        // Get all the shippingAddressList where shippingAddressStreet1 is null
        defaultShippingAddressShouldNotBeFound("shippingAddressStreet1.specified=false");
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressStreet2IsEqualToSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressStreet2 equals to DEFAULT_SHIPPING_ADDRESS_STREET_2
        defaultShippingAddressShouldBeFound("shippingAddressStreet2.equals=" + DEFAULT_SHIPPING_ADDRESS_STREET_2);

        // Get all the shippingAddressList where shippingAddressStreet2 equals to UPDATED_SHIPPING_ADDRESS_STREET_2
        defaultShippingAddressShouldNotBeFound("shippingAddressStreet2.equals=" + UPDATED_SHIPPING_ADDRESS_STREET_2);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressStreet2IsInShouldWork() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressStreet2 in DEFAULT_SHIPPING_ADDRESS_STREET_2 or UPDATED_SHIPPING_ADDRESS_STREET_2
        defaultShippingAddressShouldBeFound("shippingAddressStreet2.in=" + DEFAULT_SHIPPING_ADDRESS_STREET_2 + "," + UPDATED_SHIPPING_ADDRESS_STREET_2);

        // Get all the shippingAddressList where shippingAddressStreet2 equals to UPDATED_SHIPPING_ADDRESS_STREET_2
        defaultShippingAddressShouldNotBeFound("shippingAddressStreet2.in=" + UPDATED_SHIPPING_ADDRESS_STREET_2);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressStreet2IsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressStreet2 is not null
        defaultShippingAddressShouldBeFound("shippingAddressStreet2.specified=true");

        // Get all the shippingAddressList where shippingAddressStreet2 is null
        defaultShippingAddressShouldNotBeFound("shippingAddressStreet2.specified=false");
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressCityIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressCity equals to DEFAULT_SHIPPING_ADDRESS_CITY
        defaultShippingAddressShouldBeFound("shippingAddressCity.equals=" + DEFAULT_SHIPPING_ADDRESS_CITY);

        // Get all the shippingAddressList where shippingAddressCity equals to UPDATED_SHIPPING_ADDRESS_CITY
        defaultShippingAddressShouldNotBeFound("shippingAddressCity.equals=" + UPDATED_SHIPPING_ADDRESS_CITY);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressCityIsInShouldWork() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressCity in DEFAULT_SHIPPING_ADDRESS_CITY or UPDATED_SHIPPING_ADDRESS_CITY
        defaultShippingAddressShouldBeFound("shippingAddressCity.in=" + DEFAULT_SHIPPING_ADDRESS_CITY + "," + UPDATED_SHIPPING_ADDRESS_CITY);

        // Get all the shippingAddressList where shippingAddressCity equals to UPDATED_SHIPPING_ADDRESS_CITY
        defaultShippingAddressShouldNotBeFound("shippingAddressCity.in=" + UPDATED_SHIPPING_ADDRESS_CITY);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressCity is not null
        defaultShippingAddressShouldBeFound("shippingAddressCity.specified=true");

        // Get all the shippingAddressList where shippingAddressCity is null
        defaultShippingAddressShouldNotBeFound("shippingAddressCity.specified=false");
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressStateIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressState equals to DEFAULT_SHIPPING_ADDRESS_STATE
        defaultShippingAddressShouldBeFound("shippingAddressState.equals=" + DEFAULT_SHIPPING_ADDRESS_STATE);

        // Get all the shippingAddressList where shippingAddressState equals to UPDATED_SHIPPING_ADDRESS_STATE
        defaultShippingAddressShouldNotBeFound("shippingAddressState.equals=" + UPDATED_SHIPPING_ADDRESS_STATE);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressStateIsInShouldWork() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressState in DEFAULT_SHIPPING_ADDRESS_STATE or UPDATED_SHIPPING_ADDRESS_STATE
        defaultShippingAddressShouldBeFound("shippingAddressState.in=" + DEFAULT_SHIPPING_ADDRESS_STATE + "," + UPDATED_SHIPPING_ADDRESS_STATE);

        // Get all the shippingAddressList where shippingAddressState equals to UPDATED_SHIPPING_ADDRESS_STATE
        defaultShippingAddressShouldNotBeFound("shippingAddressState.in=" + UPDATED_SHIPPING_ADDRESS_STATE);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressState is not null
        defaultShippingAddressShouldBeFound("shippingAddressState.specified=true");

        // Get all the shippingAddressList where shippingAddressState is null
        defaultShippingAddressShouldNotBeFound("shippingAddressState.specified=false");
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressCountry equals to DEFAULT_SHIPPING_ADDRESS_COUNTRY
        defaultShippingAddressShouldBeFound("shippingAddressCountry.equals=" + DEFAULT_SHIPPING_ADDRESS_COUNTRY);

        // Get all the shippingAddressList where shippingAddressCountry equals to UPDATED_SHIPPING_ADDRESS_COUNTRY
        defaultShippingAddressShouldNotBeFound("shippingAddressCountry.equals=" + UPDATED_SHIPPING_ADDRESS_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressCountryIsInShouldWork() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressCountry in DEFAULT_SHIPPING_ADDRESS_COUNTRY or UPDATED_SHIPPING_ADDRESS_COUNTRY
        defaultShippingAddressShouldBeFound("shippingAddressCountry.in=" + DEFAULT_SHIPPING_ADDRESS_COUNTRY + "," + UPDATED_SHIPPING_ADDRESS_COUNTRY);

        // Get all the shippingAddressList where shippingAddressCountry equals to UPDATED_SHIPPING_ADDRESS_COUNTRY
        defaultShippingAddressShouldNotBeFound("shippingAddressCountry.in=" + UPDATED_SHIPPING_ADDRESS_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressCountry is not null
        defaultShippingAddressShouldBeFound("shippingAddressCountry.specified=true");

        // Get all the shippingAddressList where shippingAddressCountry is null
        defaultShippingAddressShouldNotBeFound("shippingAddressCountry.specified=false");
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressZipcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressZipcode equals to DEFAULT_SHIPPING_ADDRESS_ZIPCODE
        defaultShippingAddressShouldBeFound("shippingAddressZipcode.equals=" + DEFAULT_SHIPPING_ADDRESS_ZIPCODE);

        // Get all the shippingAddressList where shippingAddressZipcode equals to UPDATED_SHIPPING_ADDRESS_ZIPCODE
        defaultShippingAddressShouldNotBeFound("shippingAddressZipcode.equals=" + UPDATED_SHIPPING_ADDRESS_ZIPCODE);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressZipcodeIsInShouldWork() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressZipcode in DEFAULT_SHIPPING_ADDRESS_ZIPCODE or UPDATED_SHIPPING_ADDRESS_ZIPCODE
        defaultShippingAddressShouldBeFound("shippingAddressZipcode.in=" + DEFAULT_SHIPPING_ADDRESS_ZIPCODE + "," + UPDATED_SHIPPING_ADDRESS_ZIPCODE);

        // Get all the shippingAddressList where shippingAddressZipcode equals to UPDATED_SHIPPING_ADDRESS_ZIPCODE
        defaultShippingAddressShouldNotBeFound("shippingAddressZipcode.in=" + UPDATED_SHIPPING_ADDRESS_ZIPCODE);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByShippingAddressZipcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where shippingAddressZipcode is not null
        defaultShippingAddressShouldBeFound("shippingAddressZipcode.specified=true");

        // Get all the shippingAddressList where shippingAddressZipcode is null
        defaultShippingAddressShouldNotBeFound("shippingAddressZipcode.specified=false");
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByOrderedIsEqualToSomething() throws Exception {
        // Initialize the database
        Ordered ordered = OrderedResourceIntTest.createEntity(em);
        em.persist(ordered);
        em.flush();
        shippingAddress.addOrdered(ordered);
        shippingAddressRepository.saveAndFlush(shippingAddress);
        Long orderedId = ordered.getId();

        // Get all the shippingAddressList where ordered equals to orderedId
        defaultShippingAddressShouldBeFound("orderedId.equals=" + orderedId);

        // Get all the shippingAddressList where ordered equals to orderedId + 1
        defaultShippingAddressShouldNotBeFound("orderedId.equals=" + (orderedId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultShippingAddressShouldBeFound(String filter) throws Exception {
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].shippingAddressName").value(hasItem(DEFAULT_SHIPPING_ADDRESS_NAME.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressStreet1").value(hasItem(DEFAULT_SHIPPING_ADDRESS_STREET_1.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressStreet2").value(hasItem(DEFAULT_SHIPPING_ADDRESS_STREET_2.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressCity").value(hasItem(DEFAULT_SHIPPING_ADDRESS_CITY.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressState").value(hasItem(DEFAULT_SHIPPING_ADDRESS_STATE.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressCountry").value(hasItem(DEFAULT_SHIPPING_ADDRESS_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressZipcode").value(hasItem(DEFAULT_SHIPPING_ADDRESS_ZIPCODE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultShippingAddressShouldNotBeFound(String filter) throws Exception {
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingShippingAddress() throws Exception {
        // Get the shippingAddress
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressService.save(shippingAddress);

        int databaseSizeBeforeUpdate = shippingAddressRepository.findAll().size();

        // Update the shippingAddress
        ShippingAddress updatedShippingAddress = shippingAddressRepository.findOne(shippingAddress.getId());
        // Disconnect from session so that the updates on updatedShippingAddress are not directly saved in db
        em.detach(updatedShippingAddress);
        updatedShippingAddress
            .shippingAddressName(UPDATED_SHIPPING_ADDRESS_NAME)
            .shippingAddressStreet1(UPDATED_SHIPPING_ADDRESS_STREET_1)
            .shippingAddressStreet2(UPDATED_SHIPPING_ADDRESS_STREET_2)
            .shippingAddressCity(UPDATED_SHIPPING_ADDRESS_CITY)
            .shippingAddressState(UPDATED_SHIPPING_ADDRESS_STATE)
            .shippingAddressCountry(UPDATED_SHIPPING_ADDRESS_COUNTRY)
            .shippingAddressZipcode(UPDATED_SHIPPING_ADDRESS_ZIPCODE);

        restShippingAddressMockMvc.perform(put("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShippingAddress)))
            .andExpect(status().isOk());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeUpdate);
        ShippingAddress testShippingAddress = shippingAddressList.get(shippingAddressList.size() - 1);
        assertThat(testShippingAddress.getShippingAddressName()).isEqualTo(UPDATED_SHIPPING_ADDRESS_NAME);
        assertThat(testShippingAddress.getShippingAddressStreet1()).isEqualTo(UPDATED_SHIPPING_ADDRESS_STREET_1);
        assertThat(testShippingAddress.getShippingAddressStreet2()).isEqualTo(UPDATED_SHIPPING_ADDRESS_STREET_2);
        assertThat(testShippingAddress.getShippingAddressCity()).isEqualTo(UPDATED_SHIPPING_ADDRESS_CITY);
        assertThat(testShippingAddress.getShippingAddressState()).isEqualTo(UPDATED_SHIPPING_ADDRESS_STATE);
        assertThat(testShippingAddress.getShippingAddressCountry()).isEqualTo(UPDATED_SHIPPING_ADDRESS_COUNTRY);
        assertThat(testShippingAddress.getShippingAddressZipcode()).isEqualTo(UPDATED_SHIPPING_ADDRESS_ZIPCODE);

        // Validate the ShippingAddress in Elasticsearch
        ShippingAddress shippingAddressEs = shippingAddressSearchRepository.findOne(testShippingAddress.getId());
        assertThat(shippingAddressEs).isEqualToIgnoringGivenFields(testShippingAddress);
    }

    @Test
    @Transactional
    public void updateNonExistingShippingAddress() throws Exception {
        int databaseSizeBeforeUpdate = shippingAddressRepository.findAll().size();

        // Create the ShippingAddress

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShippingAddressMockMvc.perform(put("/api/shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddress)))
            .andExpect(status().isCreated());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressService.save(shippingAddress);

        int databaseSizeBeforeDelete = shippingAddressRepository.findAll().size();

        // Get the shippingAddress
        restShippingAddressMockMvc.perform(delete("/api/shipping-addresses/{id}", shippingAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean shippingAddressExistsInEs = shippingAddressSearchRepository.exists(shippingAddress.getId());
        assertThat(shippingAddressExistsInEs).isFalse();

        // Validate the database is empty
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressService.save(shippingAddress);

        // Search the shippingAddress
        restShippingAddressMockMvc.perform(get("/api/_search/shipping-addresses?query=id:" + shippingAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].shippingAddressName").value(hasItem(DEFAULT_SHIPPING_ADDRESS_NAME.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressStreet1").value(hasItem(DEFAULT_SHIPPING_ADDRESS_STREET_1.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressStreet2").value(hasItem(DEFAULT_SHIPPING_ADDRESS_STREET_2.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressCity").value(hasItem(DEFAULT_SHIPPING_ADDRESS_CITY.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressState").value(hasItem(DEFAULT_SHIPPING_ADDRESS_STATE.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressCountry").value(hasItem(DEFAULT_SHIPPING_ADDRESS_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].shippingAddressZipcode").value(hasItem(DEFAULT_SHIPPING_ADDRESS_ZIPCODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShippingAddress.class);
        ShippingAddress shippingAddress1 = new ShippingAddress();
        shippingAddress1.setId(1L);
        ShippingAddress shippingAddress2 = new ShippingAddress();
        shippingAddress2.setId(shippingAddress1.getId());
        assertThat(shippingAddress1).isEqualTo(shippingAddress2);
        shippingAddress2.setId(2L);
        assertThat(shippingAddress1).isNotEqualTo(shippingAddress2);
        shippingAddress1.setId(null);
        assertThat(shippingAddress1).isNotEqualTo(shippingAddress2);
    }
}
