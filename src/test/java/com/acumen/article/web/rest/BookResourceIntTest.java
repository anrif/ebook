package com.acumen.article.web.rest;

import com.acumen.article.ArticleApp;

import com.acumen.article.domain.Book;
import com.acumen.article.repository.BookRepository;
import com.acumen.article.service.BookService;
import com.acumen.article.repository.search.BookSearchRepository;
import com.acumen.article.web.rest.errors.ExceptionTranslator;
import com.acumen.article.service.dto.BookCriteria;
import com.acumen.article.service.BookQueryService;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.acumen.article.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.acumen.article.domain.enumeration.Language;
import com.acumen.article.domain.enumeration.BookCategory;
import com.acumen.article.domain.enumeration.BookFormat;
/**
 * Test class for the BookResource REST controller.
 *
 * @see BookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleApp.class)
public class BookResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLISHER = "AAAAAAAAAA";
    private static final String UPDATED_PUBLISHER = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLICATION_DATE = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICATION_DATE = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.FRENCH;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    private static final BookCategory DEFAULT_CATEGORY = BookCategory.MANAGEMENT;
    private static final BookCategory UPDATED_CATEGORY = BookCategory.FICTION;

    private static final Integer DEFAULT_NUMBER_OF_PAGES = 1;
    private static final Integer UPDATED_NUMBER_OF_PAGES = 2;

    private static final BookFormat DEFAULT_FORMAT = BookFormat.PAPERBACK;
    private static final BookFormat UPDATED_FORMAT = BookFormat.HARDCOVER;

    private static final Integer DEFAULT_ISBN = 1;
    private static final Integer UPDATED_ISBN = 2;

    private static final Double DEFAULT_SHIPPING_WEIGHT = 1D;
    private static final Double UPDATED_SHIPPING_WEIGHT = 2D;

    private static final Double DEFAULT_LAST_PRICE = 1D;
    private static final Double UPDATED_LAST_PRICE = 2D;

    private static final Double DEFAULT_OUR_PRICE = 1D;
    private static final Double UPDATED_OUR_PRICE = 2D;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_IN_STOCK_NUMBER = 1;
    private static final Integer UPDATED_IN_STOCK_NUMBER = 2;

    private static final byte[] DEFAULT_BOOK_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BOOK_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_BOOK_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BOOK_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookSearchRepository bookSearchRepository;

    @Autowired
    private BookQueryService bookQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookMockMvc;

    private Book book;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookResource bookResource = new BookResource(bookService, bookQueryService);
        this.restBookMockMvc = MockMvcBuilders.standaloneSetup(bookResource)
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
    public static Book createEntity(EntityManager em) {
        Book book = new Book()
            .title(DEFAULT_TITLE)
            .author(DEFAULT_AUTHOR)
            .publisher(DEFAULT_PUBLISHER)
            .publicationDate(DEFAULT_PUBLICATION_DATE)
            .language(DEFAULT_LANGUAGE)
            .category(DEFAULT_CATEGORY)
            .numberOfPages(DEFAULT_NUMBER_OF_PAGES)
            .format(DEFAULT_FORMAT)
            .isbn(DEFAULT_ISBN)
            .shippingWeight(DEFAULT_SHIPPING_WEIGHT)
            .lastPrice(DEFAULT_LAST_PRICE)
            .ourPrice(DEFAULT_OUR_PRICE)
            .active(DEFAULT_ACTIVE)
            .description(DEFAULT_DESCRIPTION)
            .inStockNumber(DEFAULT_IN_STOCK_NUMBER)
            .bookImage(DEFAULT_BOOK_IMAGE)
            .bookImageContentType(DEFAULT_BOOK_IMAGE_CONTENT_TYPE);
        return book;
    }

    @Before
    public void initTest() {
        bookSearchRepository.deleteAll();
        book = createEntity(em);
    }

    @Test
    @Transactional
    public void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book
        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBook.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBook.getPublisher()).isEqualTo(DEFAULT_PUBLISHER);
        assertThat(testBook.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
        assertThat(testBook.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testBook.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testBook.getNumberOfPages()).isEqualTo(DEFAULT_NUMBER_OF_PAGES);
        assertThat(testBook.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testBook.getIsbn()).isEqualTo(DEFAULT_ISBN);
        assertThat(testBook.getShippingWeight()).isEqualTo(DEFAULT_SHIPPING_WEIGHT);
        assertThat(testBook.getLastPrice()).isEqualTo(DEFAULT_LAST_PRICE);
        assertThat(testBook.getOurPrice()).isEqualTo(DEFAULT_OUR_PRICE);
        assertThat(testBook.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testBook.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBook.getInStockNumber()).isEqualTo(DEFAULT_IN_STOCK_NUMBER);
        assertThat(testBook.getBookImage()).isEqualTo(DEFAULT_BOOK_IMAGE);
        assertThat(testBook.getBookImageContentType()).isEqualTo(DEFAULT_BOOK_IMAGE_CONTENT_TYPE);

        // Validate the Book in Elasticsearch
        Book bookEs = bookSearchRepository.findOne(testBook.getId());
        assertThat(bookEs).isEqualToIgnoringGivenFields(testBook);
    }

    @Test
    @Transactional
    public void createBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book with an existing ID
        book.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setTitle(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAuthorIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setAuthor(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsbnIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setIsbn(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShippingWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setShippingWeight(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setLastPrice(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOurPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setOurPrice(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setActive(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setDescription(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInStockNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setInStockNumber(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList
        restBookMockMvc.perform(get("/api/books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].publisher").value(hasItem(DEFAULT_PUBLISHER.toString())))
            .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].numberOfPages").value(hasItem(DEFAULT_NUMBER_OF_PAGES)))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN)))
            .andExpect(jsonPath("$.[*].shippingWeight").value(hasItem(DEFAULT_SHIPPING_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].lastPrice").value(hasItem(DEFAULT_LAST_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].ourPrice").value(hasItem(DEFAULT_OUR_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].inStockNumber").value(hasItem(DEFAULT_IN_STOCK_NUMBER)))
            .andExpect(jsonPath("$.[*].bookImageContentType").value(hasItem(DEFAULT_BOOK_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bookImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_BOOK_IMAGE))));
    }

    @Test
    @Transactional
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(book.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.publisher").value(DEFAULT_PUBLISHER.toString()))
            .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.numberOfPages").value(DEFAULT_NUMBER_OF_PAGES))
            .andExpect(jsonPath("$.format").value(DEFAULT_FORMAT.toString()))
            .andExpect(jsonPath("$.isbn").value(DEFAULT_ISBN))
            .andExpect(jsonPath("$.shippingWeight").value(DEFAULT_SHIPPING_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.lastPrice").value(DEFAULT_LAST_PRICE.doubleValue()))
            .andExpect(jsonPath("$.ourPrice").value(DEFAULT_OUR_PRICE.doubleValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.inStockNumber").value(DEFAULT_IN_STOCK_NUMBER))
            .andExpect(jsonPath("$.bookImageContentType").value(DEFAULT_BOOK_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.bookImage").value(Base64Utils.encodeToString(DEFAULT_BOOK_IMAGE)));
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title equals to DEFAULT_TITLE
        defaultBookShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the bookList where title equals to UPDATED_TITLE
        defaultBookShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultBookShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the bookList where title equals to UPDATED_TITLE
        defaultBookShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title is not null
        defaultBookShouldBeFound("title.specified=true");

        // Get all the bookList where title is null
        defaultBookShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByAuthorIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author equals to DEFAULT_AUTHOR
        defaultBookShouldBeFound("author.equals=" + DEFAULT_AUTHOR);

        // Get all the bookList where author equals to UPDATED_AUTHOR
        defaultBookShouldNotBeFound("author.equals=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    public void getAllBooksByAuthorIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author in DEFAULT_AUTHOR or UPDATED_AUTHOR
        defaultBookShouldBeFound("author.in=" + DEFAULT_AUTHOR + "," + UPDATED_AUTHOR);

        // Get all the bookList where author equals to UPDATED_AUTHOR
        defaultBookShouldNotBeFound("author.in=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    public void getAllBooksByAuthorIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author is not null
        defaultBookShouldBeFound("author.specified=true");

        // Get all the bookList where author is null
        defaultBookShouldNotBeFound("author.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByPublisherIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publisher equals to DEFAULT_PUBLISHER
        defaultBookShouldBeFound("publisher.equals=" + DEFAULT_PUBLISHER);

        // Get all the bookList where publisher equals to UPDATED_PUBLISHER
        defaultBookShouldNotBeFound("publisher.equals=" + UPDATED_PUBLISHER);
    }

    @Test
    @Transactional
    public void getAllBooksByPublisherIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publisher in DEFAULT_PUBLISHER or UPDATED_PUBLISHER
        defaultBookShouldBeFound("publisher.in=" + DEFAULT_PUBLISHER + "," + UPDATED_PUBLISHER);

        // Get all the bookList where publisher equals to UPDATED_PUBLISHER
        defaultBookShouldNotBeFound("publisher.in=" + UPDATED_PUBLISHER);
    }

    @Test
    @Transactional
    public void getAllBooksByPublisherIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publisher is not null
        defaultBookShouldBeFound("publisher.specified=true");

        // Get all the bookList where publisher is null
        defaultBookShouldNotBeFound("publisher.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByPublicationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicationDate equals to DEFAULT_PUBLICATION_DATE
        defaultBookShouldBeFound("publicationDate.equals=" + DEFAULT_PUBLICATION_DATE);

        // Get all the bookList where publicationDate equals to UPDATED_PUBLICATION_DATE
        defaultBookShouldNotBeFound("publicationDate.equals=" + UPDATED_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllBooksByPublicationDateIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicationDate in DEFAULT_PUBLICATION_DATE or UPDATED_PUBLICATION_DATE
        defaultBookShouldBeFound("publicationDate.in=" + DEFAULT_PUBLICATION_DATE + "," + UPDATED_PUBLICATION_DATE);

        // Get all the bookList where publicationDate equals to UPDATED_PUBLICATION_DATE
        defaultBookShouldNotBeFound("publicationDate.in=" + UPDATED_PUBLICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllBooksByPublicationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicationDate is not null
        defaultBookShouldBeFound("publicationDate.specified=true");

        // Get all the bookList where publicationDate is null
        defaultBookShouldNotBeFound("publicationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language equals to DEFAULT_LANGUAGE
        defaultBookShouldBeFound("language.equals=" + DEFAULT_LANGUAGE);

        // Get all the bookList where language equals to UPDATED_LANGUAGE
        defaultBookShouldNotBeFound("language.equals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllBooksByLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language in DEFAULT_LANGUAGE or UPDATED_LANGUAGE
        defaultBookShouldBeFound("language.in=" + DEFAULT_LANGUAGE + "," + UPDATED_LANGUAGE);

        // Get all the bookList where language equals to UPDATED_LANGUAGE
        defaultBookShouldNotBeFound("language.in=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllBooksByLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language is not null
        defaultBookShouldBeFound("language.specified=true");

        // Get all the bookList where language is null
        defaultBookShouldNotBeFound("language.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where category equals to DEFAULT_CATEGORY
        defaultBookShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the bookList where category equals to UPDATED_CATEGORY
        defaultBookShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllBooksByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultBookShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the bookList where category equals to UPDATED_CATEGORY
        defaultBookShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllBooksByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where category is not null
        defaultBookShouldBeFound("category.specified=true");

        // Get all the bookList where category is null
        defaultBookShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByNumberOfPagesIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where numberOfPages equals to DEFAULT_NUMBER_OF_PAGES
        defaultBookShouldBeFound("numberOfPages.equals=" + DEFAULT_NUMBER_OF_PAGES);

        // Get all the bookList where numberOfPages equals to UPDATED_NUMBER_OF_PAGES
        defaultBookShouldNotBeFound("numberOfPages.equals=" + UPDATED_NUMBER_OF_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByNumberOfPagesIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where numberOfPages in DEFAULT_NUMBER_OF_PAGES or UPDATED_NUMBER_OF_PAGES
        defaultBookShouldBeFound("numberOfPages.in=" + DEFAULT_NUMBER_OF_PAGES + "," + UPDATED_NUMBER_OF_PAGES);

        // Get all the bookList where numberOfPages equals to UPDATED_NUMBER_OF_PAGES
        defaultBookShouldNotBeFound("numberOfPages.in=" + UPDATED_NUMBER_OF_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByNumberOfPagesIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where numberOfPages is not null
        defaultBookShouldBeFound("numberOfPages.specified=true");

        // Get all the bookList where numberOfPages is null
        defaultBookShouldNotBeFound("numberOfPages.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByNumberOfPagesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where numberOfPages greater than or equals to DEFAULT_NUMBER_OF_PAGES
        defaultBookShouldBeFound("numberOfPages.greaterOrEqualThan=" + DEFAULT_NUMBER_OF_PAGES);

        // Get all the bookList where numberOfPages greater than or equals to UPDATED_NUMBER_OF_PAGES
        defaultBookShouldNotBeFound("numberOfPages.greaterOrEqualThan=" + UPDATED_NUMBER_OF_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByNumberOfPagesIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where numberOfPages less than or equals to DEFAULT_NUMBER_OF_PAGES
        defaultBookShouldNotBeFound("numberOfPages.lessThan=" + DEFAULT_NUMBER_OF_PAGES);

        // Get all the bookList where numberOfPages less than or equals to UPDATED_NUMBER_OF_PAGES
        defaultBookShouldBeFound("numberOfPages.lessThan=" + UPDATED_NUMBER_OF_PAGES);
    }


    @Test
    @Transactional
    public void getAllBooksByFormatIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where format equals to DEFAULT_FORMAT
        defaultBookShouldBeFound("format.equals=" + DEFAULT_FORMAT);

        // Get all the bookList where format equals to UPDATED_FORMAT
        defaultBookShouldNotBeFound("format.equals=" + UPDATED_FORMAT);
    }

    @Test
    @Transactional
    public void getAllBooksByFormatIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where format in DEFAULT_FORMAT or UPDATED_FORMAT
        defaultBookShouldBeFound("format.in=" + DEFAULT_FORMAT + "," + UPDATED_FORMAT);

        // Get all the bookList where format equals to UPDATED_FORMAT
        defaultBookShouldNotBeFound("format.in=" + UPDATED_FORMAT);
    }

    @Test
    @Transactional
    public void getAllBooksByFormatIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where format is not null
        defaultBookShouldBeFound("format.specified=true");

        // Get all the bookList where format is null
        defaultBookShouldNotBeFound("format.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByIsbnIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn equals to DEFAULT_ISBN
        defaultBookShouldBeFound("isbn.equals=" + DEFAULT_ISBN);

        // Get all the bookList where isbn equals to UPDATED_ISBN
        defaultBookShouldNotBeFound("isbn.equals=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllBooksByIsbnIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn in DEFAULT_ISBN or UPDATED_ISBN
        defaultBookShouldBeFound("isbn.in=" + DEFAULT_ISBN + "," + UPDATED_ISBN);

        // Get all the bookList where isbn equals to UPDATED_ISBN
        defaultBookShouldNotBeFound("isbn.in=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllBooksByIsbnIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn is not null
        defaultBookShouldBeFound("isbn.specified=true");

        // Get all the bookList where isbn is null
        defaultBookShouldNotBeFound("isbn.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByIsbnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn greater than or equals to DEFAULT_ISBN
        defaultBookShouldBeFound("isbn.greaterOrEqualThan=" + DEFAULT_ISBN);

        // Get all the bookList where isbn greater than or equals to UPDATED_ISBN
        defaultBookShouldNotBeFound("isbn.greaterOrEqualThan=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllBooksByIsbnIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn less than or equals to DEFAULT_ISBN
        defaultBookShouldNotBeFound("isbn.lessThan=" + DEFAULT_ISBN);

        // Get all the bookList where isbn less than or equals to UPDATED_ISBN
        defaultBookShouldBeFound("isbn.lessThan=" + UPDATED_ISBN);
    }


    @Test
    @Transactional
    public void getAllBooksByShippingWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where shippingWeight equals to DEFAULT_SHIPPING_WEIGHT
        defaultBookShouldBeFound("shippingWeight.equals=" + DEFAULT_SHIPPING_WEIGHT);

        // Get all the bookList where shippingWeight equals to UPDATED_SHIPPING_WEIGHT
        defaultBookShouldNotBeFound("shippingWeight.equals=" + UPDATED_SHIPPING_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllBooksByShippingWeightIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where shippingWeight in DEFAULT_SHIPPING_WEIGHT or UPDATED_SHIPPING_WEIGHT
        defaultBookShouldBeFound("shippingWeight.in=" + DEFAULT_SHIPPING_WEIGHT + "," + UPDATED_SHIPPING_WEIGHT);

        // Get all the bookList where shippingWeight equals to UPDATED_SHIPPING_WEIGHT
        defaultBookShouldNotBeFound("shippingWeight.in=" + UPDATED_SHIPPING_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllBooksByShippingWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where shippingWeight is not null
        defaultBookShouldBeFound("shippingWeight.specified=true");

        // Get all the bookList where shippingWeight is null
        defaultBookShouldNotBeFound("shippingWeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByLastPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where lastPrice equals to DEFAULT_LAST_PRICE
        defaultBookShouldBeFound("lastPrice.equals=" + DEFAULT_LAST_PRICE);

        // Get all the bookList where lastPrice equals to UPDATED_LAST_PRICE
        defaultBookShouldNotBeFound("lastPrice.equals=" + UPDATED_LAST_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByLastPriceIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where lastPrice in DEFAULT_LAST_PRICE or UPDATED_LAST_PRICE
        defaultBookShouldBeFound("lastPrice.in=" + DEFAULT_LAST_PRICE + "," + UPDATED_LAST_PRICE);

        // Get all the bookList where lastPrice equals to UPDATED_LAST_PRICE
        defaultBookShouldNotBeFound("lastPrice.in=" + UPDATED_LAST_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByLastPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where lastPrice is not null
        defaultBookShouldBeFound("lastPrice.specified=true");

        // Get all the bookList where lastPrice is null
        defaultBookShouldNotBeFound("lastPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByOurPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ourPrice equals to DEFAULT_OUR_PRICE
        defaultBookShouldBeFound("ourPrice.equals=" + DEFAULT_OUR_PRICE);

        // Get all the bookList where ourPrice equals to UPDATED_OUR_PRICE
        defaultBookShouldNotBeFound("ourPrice.equals=" + UPDATED_OUR_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByOurPriceIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ourPrice in DEFAULT_OUR_PRICE or UPDATED_OUR_PRICE
        defaultBookShouldBeFound("ourPrice.in=" + DEFAULT_OUR_PRICE + "," + UPDATED_OUR_PRICE);

        // Get all the bookList where ourPrice equals to UPDATED_OUR_PRICE
        defaultBookShouldNotBeFound("ourPrice.in=" + UPDATED_OUR_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByOurPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ourPrice is not null
        defaultBookShouldBeFound("ourPrice.specified=true");

        // Get all the bookList where ourPrice is null
        defaultBookShouldNotBeFound("ourPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where active equals to DEFAULT_ACTIVE
        defaultBookShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the bookList where active equals to UPDATED_ACTIVE
        defaultBookShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllBooksByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultBookShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the bookList where active equals to UPDATED_ACTIVE
        defaultBookShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllBooksByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where active is not null
        defaultBookShouldBeFound("active.specified=true");

        // Get all the bookList where active is null
        defaultBookShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByInStockNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where inStockNumber equals to DEFAULT_IN_STOCK_NUMBER
        defaultBookShouldBeFound("inStockNumber.equals=" + DEFAULT_IN_STOCK_NUMBER);

        // Get all the bookList where inStockNumber equals to UPDATED_IN_STOCK_NUMBER
        defaultBookShouldNotBeFound("inStockNumber.equals=" + UPDATED_IN_STOCK_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBooksByInStockNumberIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where inStockNumber in DEFAULT_IN_STOCK_NUMBER or UPDATED_IN_STOCK_NUMBER
        defaultBookShouldBeFound("inStockNumber.in=" + DEFAULT_IN_STOCK_NUMBER + "," + UPDATED_IN_STOCK_NUMBER);

        // Get all the bookList where inStockNumber equals to UPDATED_IN_STOCK_NUMBER
        defaultBookShouldNotBeFound("inStockNumber.in=" + UPDATED_IN_STOCK_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBooksByInStockNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where inStockNumber is not null
        defaultBookShouldBeFound("inStockNumber.specified=true");

        // Get all the bookList where inStockNumber is null
        defaultBookShouldNotBeFound("inStockNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByInStockNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where inStockNumber greater than or equals to DEFAULT_IN_STOCK_NUMBER
        defaultBookShouldBeFound("inStockNumber.greaterOrEqualThan=" + DEFAULT_IN_STOCK_NUMBER);

        // Get all the bookList where inStockNumber greater than or equals to UPDATED_IN_STOCK_NUMBER
        defaultBookShouldNotBeFound("inStockNumber.greaterOrEqualThan=" + UPDATED_IN_STOCK_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBooksByInStockNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where inStockNumber less than or equals to DEFAULT_IN_STOCK_NUMBER
        defaultBookShouldNotBeFound("inStockNumber.lessThan=" + DEFAULT_IN_STOCK_NUMBER);

        // Get all the bookList where inStockNumber less than or equals to UPDATED_IN_STOCK_NUMBER
        defaultBookShouldBeFound("inStockNumber.lessThan=" + UPDATED_IN_STOCK_NUMBER);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBookShouldBeFound(String filter) throws Exception {
        restBookMockMvc.perform(get("/api/books?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].publisher").value(hasItem(DEFAULT_PUBLISHER.toString())))
            .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].numberOfPages").value(hasItem(DEFAULT_NUMBER_OF_PAGES)))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN)))
            .andExpect(jsonPath("$.[*].shippingWeight").value(hasItem(DEFAULT_SHIPPING_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].lastPrice").value(hasItem(DEFAULT_LAST_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].ourPrice").value(hasItem(DEFAULT_OUR_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].inStockNumber").value(hasItem(DEFAULT_IN_STOCK_NUMBER)))
            .andExpect(jsonPath("$.[*].bookImageContentType").value(hasItem(DEFAULT_BOOK_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bookImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_BOOK_IMAGE))));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBookShouldNotBeFound(String filter) throws Exception {
        restBookMockMvc.perform(get("/api/books?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBook() throws Exception {
        // Initialize the database
        bookService.save(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findOne(book.getId());
        // Disconnect from session so that the updates on updatedBook are not directly saved in db
        em.detach(updatedBook);
        updatedBook
            .title(UPDATED_TITLE)
            .author(UPDATED_AUTHOR)
            .publisher(UPDATED_PUBLISHER)
            .publicationDate(UPDATED_PUBLICATION_DATE)
            .language(UPDATED_LANGUAGE)
            .category(UPDATED_CATEGORY)
            .numberOfPages(UPDATED_NUMBER_OF_PAGES)
            .format(UPDATED_FORMAT)
            .isbn(UPDATED_ISBN)
            .shippingWeight(UPDATED_SHIPPING_WEIGHT)
            .lastPrice(UPDATED_LAST_PRICE)
            .ourPrice(UPDATED_OUR_PRICE)
            .active(UPDATED_ACTIVE)
            .description(UPDATED_DESCRIPTION)
            .inStockNumber(UPDATED_IN_STOCK_NUMBER)
            .bookImage(UPDATED_BOOK_IMAGE)
            .bookImageContentType(UPDATED_BOOK_IMAGE_CONTENT_TYPE);

        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBook)))
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBook.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBook.getPublisher()).isEqualTo(UPDATED_PUBLISHER);
        assertThat(testBook.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
        assertThat(testBook.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testBook.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testBook.getNumberOfPages()).isEqualTo(UPDATED_NUMBER_OF_PAGES);
        assertThat(testBook.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testBook.getIsbn()).isEqualTo(UPDATED_ISBN);
        assertThat(testBook.getShippingWeight()).isEqualTo(UPDATED_SHIPPING_WEIGHT);
        assertThat(testBook.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testBook.getOurPrice()).isEqualTo(UPDATED_OUR_PRICE);
        assertThat(testBook.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testBook.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBook.getInStockNumber()).isEqualTo(UPDATED_IN_STOCK_NUMBER);
        assertThat(testBook.getBookImage()).isEqualTo(UPDATED_BOOK_IMAGE);
        assertThat(testBook.getBookImageContentType()).isEqualTo(UPDATED_BOOK_IMAGE_CONTENT_TYPE);

        // Validate the Book in Elasticsearch
        Book bookEs = bookSearchRepository.findOne(testBook.getId());
        assertThat(bookEs).isEqualToIgnoringGivenFields(testBook);
    }

    @Test
    @Transactional
    public void updateNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Create the Book

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBook() throws Exception {
        // Initialize the database
        bookService.save(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Get the book
        restBookMockMvc.perform(delete("/api/books/{id}", book.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bookExistsInEs = bookSearchRepository.exists(book.getId());
        assertThat(bookExistsInEs).isFalse();

        // Validate the database is empty
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBook() throws Exception {
        // Initialize the database
        bookService.save(book);

        // Search the book
        restBookMockMvc.perform(get("/api/_search/books?query=id:" + book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].publisher").value(hasItem(DEFAULT_PUBLISHER.toString())))
            .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].numberOfPages").value(hasItem(DEFAULT_NUMBER_OF_PAGES)))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN)))
            .andExpect(jsonPath("$.[*].shippingWeight").value(hasItem(DEFAULT_SHIPPING_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].lastPrice").value(hasItem(DEFAULT_LAST_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].ourPrice").value(hasItem(DEFAULT_OUR_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].inStockNumber").value(hasItem(DEFAULT_IN_STOCK_NUMBER)))
            .andExpect(jsonPath("$.[*].bookImageContentType").value(hasItem(DEFAULT_BOOK_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bookImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_BOOK_IMAGE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Book.class);
        Book book1 = new Book();
        book1.setId(1L);
        Book book2 = new Book();
        book2.setId(book1.getId());
        assertThat(book1).isEqualTo(book2);
        book2.setId(2L);
        assertThat(book1).isNotEqualTo(book2);
        book1.setId(null);
        assertThat(book1).isNotEqualTo(book2);
    }
}
