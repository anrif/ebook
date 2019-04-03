package com.acumen.article.web.rest;

import com.acumen.article.ArticleApp;

import com.acumen.article.domain.BillingAddress;
import com.acumen.article.domain.Ordered;
import com.acumen.article.repository.BillingAddressRepository;
import com.acumen.article.service.BillingAddressService;
import com.acumen.article.repository.search.BillingAddressSearchRepository;
import com.acumen.article.web.rest.errors.ExceptionTranslator;
import com.acumen.article.service.dto.BillingAddressCriteria;
import com.acumen.article.service.BillingAddressQueryService;

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
 * Test class for the BillingAddressResource REST controller.
 *
 * @see BillingAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleApp.class)
public class BillingAddressResourceIntTest {

    private static final String DEFAULT_BILLING_ADDRESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ADDRESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_ADDRESS_STREET_1 = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ADDRESS_STREET_1 = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_ADDRESS_STREET_2 = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ADDRESS_STREET_2 = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_ADDRESS_CITY = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ADDRESS_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_ADDRESS_STATE = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ADDRESS_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_ADDRESS_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ADDRESS_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_ADDRESS_ZIPCODE = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ADDRESS_ZIPCODE = "BBBBBBBBBB";

    @Autowired
    private BillingAddressRepository billingAddressRepository;

    @Autowired
    private BillingAddressService billingAddressService;

    @Autowired
    private BillingAddressSearchRepository billingAddressSearchRepository;

    @Autowired
    private BillingAddressQueryService billingAddressQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBillingAddressMockMvc;

    private BillingAddress billingAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillingAddressResource billingAddressResource = new BillingAddressResource(billingAddressService, billingAddressQueryService);
        this.restBillingAddressMockMvc = MockMvcBuilders.standaloneSetup(billingAddressResource)
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
    public static BillingAddress createEntity(EntityManager em) {
        BillingAddress billingAddress = new BillingAddress()
            .billingAddressName(DEFAULT_BILLING_ADDRESS_NAME)
            .billingAddressStreet1(DEFAULT_BILLING_ADDRESS_STREET_1)
            .billingAddressStreet2(DEFAULT_BILLING_ADDRESS_STREET_2)
            .billingAddressCity(DEFAULT_BILLING_ADDRESS_CITY)
            .billingAddressState(DEFAULT_BILLING_ADDRESS_STATE)
            .billingAddressCountry(DEFAULT_BILLING_ADDRESS_COUNTRY)
            .billingAddressZipcode(DEFAULT_BILLING_ADDRESS_ZIPCODE);
        return billingAddress;
    }

    @Before
    public void initTest() {
        billingAddressSearchRepository.deleteAll();
        billingAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillingAddress() throws Exception {
        int databaseSizeBeforeCreate = billingAddressRepository.findAll().size();

        // Create the BillingAddress
        restBillingAddressMockMvc.perform(post("/api/billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingAddress)))
            .andExpect(status().isCreated());

        // Validate the BillingAddress in the database
        List<BillingAddress> billingAddressList = billingAddressRepository.findAll();
        assertThat(billingAddressList).hasSize(databaseSizeBeforeCreate + 1);
        BillingAddress testBillingAddress = billingAddressList.get(billingAddressList.size() - 1);
        assertThat(testBillingAddress.getBillingAddressName()).isEqualTo(DEFAULT_BILLING_ADDRESS_NAME);
        assertThat(testBillingAddress.getBillingAddressStreet1()).isEqualTo(DEFAULT_BILLING_ADDRESS_STREET_1);
        assertThat(testBillingAddress.getBillingAddressStreet2()).isEqualTo(DEFAULT_BILLING_ADDRESS_STREET_2);
        assertThat(testBillingAddress.getBillingAddressCity()).isEqualTo(DEFAULT_BILLING_ADDRESS_CITY);
        assertThat(testBillingAddress.getBillingAddressState()).isEqualTo(DEFAULT_BILLING_ADDRESS_STATE);
        assertThat(testBillingAddress.getBillingAddressCountry()).isEqualTo(DEFAULT_BILLING_ADDRESS_COUNTRY);
        assertThat(testBillingAddress.getBillingAddressZipcode()).isEqualTo(DEFAULT_BILLING_ADDRESS_ZIPCODE);

        // Validate the BillingAddress in Elasticsearch
        BillingAddress billingAddressEs = billingAddressSearchRepository.findOne(testBillingAddress.getId());
        assertThat(billingAddressEs).isEqualToIgnoringGivenFields(testBillingAddress);
    }

    @Test
    @Transactional
    public void createBillingAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingAddressRepository.findAll().size();

        // Create the BillingAddress with an existing ID
        billingAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingAddressMockMvc.perform(post("/api/billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingAddress)))
            .andExpect(status().isBadRequest());

        // Validate the BillingAddress in the database
        List<BillingAddress> billingAddressList = billingAddressRepository.findAll();
        assertThat(billingAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBillingAddressNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingAddressRepository.findAll().size();
        // set the field null
        billingAddress.setBillingAddressName(null);

        // Create the BillingAddress, which fails.

        restBillingAddressMockMvc.perform(post("/api/billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingAddress)))
            .andExpect(status().isBadRequest());

        List<BillingAddress> billingAddressList = billingAddressRepository.findAll();
        assertThat(billingAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBillingAddressCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingAddressRepository.findAll().size();
        // set the field null
        billingAddress.setBillingAddressCity(null);

        // Create the BillingAddress, which fails.

        restBillingAddressMockMvc.perform(post("/api/billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingAddress)))
            .andExpect(status().isBadRequest());

        List<BillingAddress> billingAddressList = billingAddressRepository.findAll();
        assertThat(billingAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBillingAddressStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingAddressRepository.findAll().size();
        // set the field null
        billingAddress.setBillingAddressState(null);

        // Create the BillingAddress, which fails.

        restBillingAddressMockMvc.perform(post("/api/billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingAddress)))
            .andExpect(status().isBadRequest());

        List<BillingAddress> billingAddressList = billingAddressRepository.findAll();
        assertThat(billingAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBillingAddressCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingAddressRepository.findAll().size();
        // set the field null
        billingAddress.setBillingAddressCountry(null);

        // Create the BillingAddress, which fails.

        restBillingAddressMockMvc.perform(post("/api/billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingAddress)))
            .andExpect(status().isBadRequest());

        List<BillingAddress> billingAddressList = billingAddressRepository.findAll();
        assertThat(billingAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBillingAddresses() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList
        restBillingAddressMockMvc.perform(get("/api/billing-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].billingAddressName").value(hasItem(DEFAULT_BILLING_ADDRESS_NAME.toString())))
            .andExpect(jsonPath("$.[*].billingAddressStreet1").value(hasItem(DEFAULT_BILLING_ADDRESS_STREET_1.toString())))
            .andExpect(jsonPath("$.[*].billingAddressStreet2").value(hasItem(DEFAULT_BILLING_ADDRESS_STREET_2.toString())))
            .andExpect(jsonPath("$.[*].billingAddressCity").value(hasItem(DEFAULT_BILLING_ADDRESS_CITY.toString())))
            .andExpect(jsonPath("$.[*].billingAddressState").value(hasItem(DEFAULT_BILLING_ADDRESS_STATE.toString())))
            .andExpect(jsonPath("$.[*].billingAddressCountry").value(hasItem(DEFAULT_BILLING_ADDRESS_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].billingAddressZipcode").value(hasItem(DEFAULT_BILLING_ADDRESS_ZIPCODE.toString())));
    }

    @Test
    @Transactional
    public void getBillingAddress() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get the billingAddress
        restBillingAddressMockMvc.perform(get("/api/billing-addresses/{id}", billingAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billingAddress.getId().intValue()))
            .andExpect(jsonPath("$.billingAddressName").value(DEFAULT_BILLING_ADDRESS_NAME.toString()))
            .andExpect(jsonPath("$.billingAddressStreet1").value(DEFAULT_BILLING_ADDRESS_STREET_1.toString()))
            .andExpect(jsonPath("$.billingAddressStreet2").value(DEFAULT_BILLING_ADDRESS_STREET_2.toString()))
            .andExpect(jsonPath("$.billingAddressCity").value(DEFAULT_BILLING_ADDRESS_CITY.toString()))
            .andExpect(jsonPath("$.billingAddressState").value(DEFAULT_BILLING_ADDRESS_STATE.toString()))
            .andExpect(jsonPath("$.billingAddressCountry").value(DEFAULT_BILLING_ADDRESS_COUNTRY.toString()))
            .andExpect(jsonPath("$.billingAddressZipcode").value(DEFAULT_BILLING_ADDRESS_ZIPCODE.toString()));
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressNameIsEqualToSomething() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressName equals to DEFAULT_BILLING_ADDRESS_NAME
        defaultBillingAddressShouldBeFound("billingAddressName.equals=" + DEFAULT_BILLING_ADDRESS_NAME);

        // Get all the billingAddressList where billingAddressName equals to UPDATED_BILLING_ADDRESS_NAME
        defaultBillingAddressShouldNotBeFound("billingAddressName.equals=" + UPDATED_BILLING_ADDRESS_NAME);
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressNameIsInShouldWork() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressName in DEFAULT_BILLING_ADDRESS_NAME or UPDATED_BILLING_ADDRESS_NAME
        defaultBillingAddressShouldBeFound("billingAddressName.in=" + DEFAULT_BILLING_ADDRESS_NAME + "," + UPDATED_BILLING_ADDRESS_NAME);

        // Get all the billingAddressList where billingAddressName equals to UPDATED_BILLING_ADDRESS_NAME
        defaultBillingAddressShouldNotBeFound("billingAddressName.in=" + UPDATED_BILLING_ADDRESS_NAME);
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressName is not null
        defaultBillingAddressShouldBeFound("billingAddressName.specified=true");

        // Get all the billingAddressList where billingAddressName is null
        defaultBillingAddressShouldNotBeFound("billingAddressName.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressStreet1IsEqualToSomething() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressStreet1 equals to DEFAULT_BILLING_ADDRESS_STREET_1
        defaultBillingAddressShouldBeFound("billingAddressStreet1.equals=" + DEFAULT_BILLING_ADDRESS_STREET_1);

        // Get all the billingAddressList where billingAddressStreet1 equals to UPDATED_BILLING_ADDRESS_STREET_1
        defaultBillingAddressShouldNotBeFound("billingAddressStreet1.equals=" + UPDATED_BILLING_ADDRESS_STREET_1);
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressStreet1IsInShouldWork() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressStreet1 in DEFAULT_BILLING_ADDRESS_STREET_1 or UPDATED_BILLING_ADDRESS_STREET_1
        defaultBillingAddressShouldBeFound("billingAddressStreet1.in=" + DEFAULT_BILLING_ADDRESS_STREET_1 + "," + UPDATED_BILLING_ADDRESS_STREET_1);

        // Get all the billingAddressList where billingAddressStreet1 equals to UPDATED_BILLING_ADDRESS_STREET_1
        defaultBillingAddressShouldNotBeFound("billingAddressStreet1.in=" + UPDATED_BILLING_ADDRESS_STREET_1);
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressStreet1IsNullOrNotNull() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressStreet1 is not null
        defaultBillingAddressShouldBeFound("billingAddressStreet1.specified=true");

        // Get all the billingAddressList where billingAddressStreet1 is null
        defaultBillingAddressShouldNotBeFound("billingAddressStreet1.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressStreet2IsEqualToSomething() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressStreet2 equals to DEFAULT_BILLING_ADDRESS_STREET_2
        defaultBillingAddressShouldBeFound("billingAddressStreet2.equals=" + DEFAULT_BILLING_ADDRESS_STREET_2);

        // Get all the billingAddressList where billingAddressStreet2 equals to UPDATED_BILLING_ADDRESS_STREET_2
        defaultBillingAddressShouldNotBeFound("billingAddressStreet2.equals=" + UPDATED_BILLING_ADDRESS_STREET_2);
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressStreet2IsInShouldWork() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressStreet2 in DEFAULT_BILLING_ADDRESS_STREET_2 or UPDATED_BILLING_ADDRESS_STREET_2
        defaultBillingAddressShouldBeFound("billingAddressStreet2.in=" + DEFAULT_BILLING_ADDRESS_STREET_2 + "," + UPDATED_BILLING_ADDRESS_STREET_2);

        // Get all the billingAddressList where billingAddressStreet2 equals to UPDATED_BILLING_ADDRESS_STREET_2
        defaultBillingAddressShouldNotBeFound("billingAddressStreet2.in=" + UPDATED_BILLING_ADDRESS_STREET_2);
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressStreet2IsNullOrNotNull() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressStreet2 is not null
        defaultBillingAddressShouldBeFound("billingAddressStreet2.specified=true");

        // Get all the billingAddressList where billingAddressStreet2 is null
        defaultBillingAddressShouldNotBeFound("billingAddressStreet2.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressCityIsEqualToSomething() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressCity equals to DEFAULT_BILLING_ADDRESS_CITY
        defaultBillingAddressShouldBeFound("billingAddressCity.equals=" + DEFAULT_BILLING_ADDRESS_CITY);

        // Get all the billingAddressList where billingAddressCity equals to UPDATED_BILLING_ADDRESS_CITY
        defaultBillingAddressShouldNotBeFound("billingAddressCity.equals=" + UPDATED_BILLING_ADDRESS_CITY);
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressCityIsInShouldWork() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressCity in DEFAULT_BILLING_ADDRESS_CITY or UPDATED_BILLING_ADDRESS_CITY
        defaultBillingAddressShouldBeFound("billingAddressCity.in=" + DEFAULT_BILLING_ADDRESS_CITY + "," + UPDATED_BILLING_ADDRESS_CITY);

        // Get all the billingAddressList where billingAddressCity equals to UPDATED_BILLING_ADDRESS_CITY
        defaultBillingAddressShouldNotBeFound("billingAddressCity.in=" + UPDATED_BILLING_ADDRESS_CITY);
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressCity is not null
        defaultBillingAddressShouldBeFound("billingAddressCity.specified=true");

        // Get all the billingAddressList where billingAddressCity is null
        defaultBillingAddressShouldNotBeFound("billingAddressCity.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressStateIsEqualToSomething() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressState equals to DEFAULT_BILLING_ADDRESS_STATE
        defaultBillingAddressShouldBeFound("billingAddressState.equals=" + DEFAULT_BILLING_ADDRESS_STATE);

        // Get all the billingAddressList where billingAddressState equals to UPDATED_BILLING_ADDRESS_STATE
        defaultBillingAddressShouldNotBeFound("billingAddressState.equals=" + UPDATED_BILLING_ADDRESS_STATE);
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressStateIsInShouldWork() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressState in DEFAULT_BILLING_ADDRESS_STATE or UPDATED_BILLING_ADDRESS_STATE
        defaultBillingAddressShouldBeFound("billingAddressState.in=" + DEFAULT_BILLING_ADDRESS_STATE + "," + UPDATED_BILLING_ADDRESS_STATE);

        // Get all the billingAddressList where billingAddressState equals to UPDATED_BILLING_ADDRESS_STATE
        defaultBillingAddressShouldNotBeFound("billingAddressState.in=" + UPDATED_BILLING_ADDRESS_STATE);
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressState is not null
        defaultBillingAddressShouldBeFound("billingAddressState.specified=true");

        // Get all the billingAddressList where billingAddressState is null
        defaultBillingAddressShouldNotBeFound("billingAddressState.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressCountry equals to DEFAULT_BILLING_ADDRESS_COUNTRY
        defaultBillingAddressShouldBeFound("billingAddressCountry.equals=" + DEFAULT_BILLING_ADDRESS_COUNTRY);

        // Get all the billingAddressList where billingAddressCountry equals to UPDATED_BILLING_ADDRESS_COUNTRY
        defaultBillingAddressShouldNotBeFound("billingAddressCountry.equals=" + UPDATED_BILLING_ADDRESS_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressCountryIsInShouldWork() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressCountry in DEFAULT_BILLING_ADDRESS_COUNTRY or UPDATED_BILLING_ADDRESS_COUNTRY
        defaultBillingAddressShouldBeFound("billingAddressCountry.in=" + DEFAULT_BILLING_ADDRESS_COUNTRY + "," + UPDATED_BILLING_ADDRESS_COUNTRY);

        // Get all the billingAddressList where billingAddressCountry equals to UPDATED_BILLING_ADDRESS_COUNTRY
        defaultBillingAddressShouldNotBeFound("billingAddressCountry.in=" + UPDATED_BILLING_ADDRESS_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressCountry is not null
        defaultBillingAddressShouldBeFound("billingAddressCountry.specified=true");

        // Get all the billingAddressList where billingAddressCountry is null
        defaultBillingAddressShouldNotBeFound("billingAddressCountry.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressZipcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressZipcode equals to DEFAULT_BILLING_ADDRESS_ZIPCODE
        defaultBillingAddressShouldBeFound("billingAddressZipcode.equals=" + DEFAULT_BILLING_ADDRESS_ZIPCODE);

        // Get all the billingAddressList where billingAddressZipcode equals to UPDATED_BILLING_ADDRESS_ZIPCODE
        defaultBillingAddressShouldNotBeFound("billingAddressZipcode.equals=" + UPDATED_BILLING_ADDRESS_ZIPCODE);
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressZipcodeIsInShouldWork() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressZipcode in DEFAULT_BILLING_ADDRESS_ZIPCODE or UPDATED_BILLING_ADDRESS_ZIPCODE
        defaultBillingAddressShouldBeFound("billingAddressZipcode.in=" + DEFAULT_BILLING_ADDRESS_ZIPCODE + "," + UPDATED_BILLING_ADDRESS_ZIPCODE);

        // Get all the billingAddressList where billingAddressZipcode equals to UPDATED_BILLING_ADDRESS_ZIPCODE
        defaultBillingAddressShouldNotBeFound("billingAddressZipcode.in=" + UPDATED_BILLING_ADDRESS_ZIPCODE);
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByBillingAddressZipcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        billingAddressRepository.saveAndFlush(billingAddress);

        // Get all the billingAddressList where billingAddressZipcode is not null
        defaultBillingAddressShouldBeFound("billingAddressZipcode.specified=true");

        // Get all the billingAddressList where billingAddressZipcode is null
        defaultBillingAddressShouldNotBeFound("billingAddressZipcode.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillingAddressesByOrderedIsEqualToSomething() throws Exception {
        // Initialize the database
        Ordered ordered = OrderedResourceIntTest.createEntity(em);
        em.persist(ordered);
        em.flush();
        billingAddress.addOrdered(ordered);
        billingAddressRepository.saveAndFlush(billingAddress);
        Long orderedId = ordered.getId();

        // Get all the billingAddressList where ordered equals to orderedId
        defaultBillingAddressShouldBeFound("orderedId.equals=" + orderedId);

        // Get all the billingAddressList where ordered equals to orderedId + 1
        defaultBillingAddressShouldNotBeFound("orderedId.equals=" + (orderedId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBillingAddressShouldBeFound(String filter) throws Exception {
        restBillingAddressMockMvc.perform(get("/api/billing-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].billingAddressName").value(hasItem(DEFAULT_BILLING_ADDRESS_NAME.toString())))
            .andExpect(jsonPath("$.[*].billingAddressStreet1").value(hasItem(DEFAULT_BILLING_ADDRESS_STREET_1.toString())))
            .andExpect(jsonPath("$.[*].billingAddressStreet2").value(hasItem(DEFAULT_BILLING_ADDRESS_STREET_2.toString())))
            .andExpect(jsonPath("$.[*].billingAddressCity").value(hasItem(DEFAULT_BILLING_ADDRESS_CITY.toString())))
            .andExpect(jsonPath("$.[*].billingAddressState").value(hasItem(DEFAULT_BILLING_ADDRESS_STATE.toString())))
            .andExpect(jsonPath("$.[*].billingAddressCountry").value(hasItem(DEFAULT_BILLING_ADDRESS_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].billingAddressZipcode").value(hasItem(DEFAULT_BILLING_ADDRESS_ZIPCODE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBillingAddressShouldNotBeFound(String filter) throws Exception {
        restBillingAddressMockMvc.perform(get("/api/billing-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingBillingAddress() throws Exception {
        // Get the billingAddress
        restBillingAddressMockMvc.perform(get("/api/billing-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillingAddress() throws Exception {
        // Initialize the database
        billingAddressService.save(billingAddress);

        int databaseSizeBeforeUpdate = billingAddressRepository.findAll().size();

        // Update the billingAddress
        BillingAddress updatedBillingAddress = billingAddressRepository.findOne(billingAddress.getId());
        // Disconnect from session so that the updates on updatedBillingAddress are not directly saved in db
        em.detach(updatedBillingAddress);
        updatedBillingAddress
            .billingAddressName(UPDATED_BILLING_ADDRESS_NAME)
            .billingAddressStreet1(UPDATED_BILLING_ADDRESS_STREET_1)
            .billingAddressStreet2(UPDATED_BILLING_ADDRESS_STREET_2)
            .billingAddressCity(UPDATED_BILLING_ADDRESS_CITY)
            .billingAddressState(UPDATED_BILLING_ADDRESS_STATE)
            .billingAddressCountry(UPDATED_BILLING_ADDRESS_COUNTRY)
            .billingAddressZipcode(UPDATED_BILLING_ADDRESS_ZIPCODE);

        restBillingAddressMockMvc.perform(put("/api/billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBillingAddress)))
            .andExpect(status().isOk());

        // Validate the BillingAddress in the database
        List<BillingAddress> billingAddressList = billingAddressRepository.findAll();
        assertThat(billingAddressList).hasSize(databaseSizeBeforeUpdate);
        BillingAddress testBillingAddress = billingAddressList.get(billingAddressList.size() - 1);
        assertThat(testBillingAddress.getBillingAddressName()).isEqualTo(UPDATED_BILLING_ADDRESS_NAME);
        assertThat(testBillingAddress.getBillingAddressStreet1()).isEqualTo(UPDATED_BILLING_ADDRESS_STREET_1);
        assertThat(testBillingAddress.getBillingAddressStreet2()).isEqualTo(UPDATED_BILLING_ADDRESS_STREET_2);
        assertThat(testBillingAddress.getBillingAddressCity()).isEqualTo(UPDATED_BILLING_ADDRESS_CITY);
        assertThat(testBillingAddress.getBillingAddressState()).isEqualTo(UPDATED_BILLING_ADDRESS_STATE);
        assertThat(testBillingAddress.getBillingAddressCountry()).isEqualTo(UPDATED_BILLING_ADDRESS_COUNTRY);
        assertThat(testBillingAddress.getBillingAddressZipcode()).isEqualTo(UPDATED_BILLING_ADDRESS_ZIPCODE);

        // Validate the BillingAddress in Elasticsearch
        BillingAddress billingAddressEs = billingAddressSearchRepository.findOne(testBillingAddress.getId());
        assertThat(billingAddressEs).isEqualToIgnoringGivenFields(testBillingAddress);
    }

    @Test
    @Transactional
    public void updateNonExistingBillingAddress() throws Exception {
        int databaseSizeBeforeUpdate = billingAddressRepository.findAll().size();

        // Create the BillingAddress

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBillingAddressMockMvc.perform(put("/api/billing-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingAddress)))
            .andExpect(status().isCreated());

        // Validate the BillingAddress in the database
        List<BillingAddress> billingAddressList = billingAddressRepository.findAll();
        assertThat(billingAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBillingAddress() throws Exception {
        // Initialize the database
        billingAddressService.save(billingAddress);

        int databaseSizeBeforeDelete = billingAddressRepository.findAll().size();

        // Get the billingAddress
        restBillingAddressMockMvc.perform(delete("/api/billing-addresses/{id}", billingAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean billingAddressExistsInEs = billingAddressSearchRepository.exists(billingAddress.getId());
        assertThat(billingAddressExistsInEs).isFalse();

        // Validate the database is empty
        List<BillingAddress> billingAddressList = billingAddressRepository.findAll();
        assertThat(billingAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBillingAddress() throws Exception {
        // Initialize the database
        billingAddressService.save(billingAddress);

        // Search the billingAddress
        restBillingAddressMockMvc.perform(get("/api/_search/billing-addresses?query=id:" + billingAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].billingAddressName").value(hasItem(DEFAULT_BILLING_ADDRESS_NAME.toString())))
            .andExpect(jsonPath("$.[*].billingAddressStreet1").value(hasItem(DEFAULT_BILLING_ADDRESS_STREET_1.toString())))
            .andExpect(jsonPath("$.[*].billingAddressStreet2").value(hasItem(DEFAULT_BILLING_ADDRESS_STREET_2.toString())))
            .andExpect(jsonPath("$.[*].billingAddressCity").value(hasItem(DEFAULT_BILLING_ADDRESS_CITY.toString())))
            .andExpect(jsonPath("$.[*].billingAddressState").value(hasItem(DEFAULT_BILLING_ADDRESS_STATE.toString())))
            .andExpect(jsonPath("$.[*].billingAddressCountry").value(hasItem(DEFAULT_BILLING_ADDRESS_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].billingAddressZipcode").value(hasItem(DEFAULT_BILLING_ADDRESS_ZIPCODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingAddress.class);
        BillingAddress billingAddress1 = new BillingAddress();
        billingAddress1.setId(1L);
        BillingAddress billingAddress2 = new BillingAddress();
        billingAddress2.setId(billingAddress1.getId());
        assertThat(billingAddress1).isEqualTo(billingAddress2);
        billingAddress2.setId(2L);
        assertThat(billingAddress1).isNotEqualTo(billingAddress2);
        billingAddress1.setId(null);
        assertThat(billingAddress1).isNotEqualTo(billingAddress2);
    }
}
