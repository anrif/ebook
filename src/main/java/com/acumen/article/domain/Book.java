package com.acumen.article.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.acumen.article.domain.enumeration.Language;

import com.acumen.article.domain.enumeration.BookCategory;

import com.acumen.article.domain.enumeration.BookFormat;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publication_date")
    private String publicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private BookCategory category;

    @Column(name = "number_of_pages")
    private Integer numberOfPages;

    @Enumerated(EnumType.STRING)
    @Column(name = "format")
    private BookFormat format;

    @NotNull
    @Column(name = "isbn", nullable = false)
    private Integer isbn;

    @NotNull
    @Column(name = "shipping_weight", nullable = false)
    private Double shippingWeight;

    @NotNull
    @Column(name = "last_price", nullable = false)
    private Double lastPrice;

    @NotNull
    @Column(name = "our_price", nullable = false)
    private Double ourPrice;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @NotNull
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "in_stock_number", nullable = false)
    private Integer inStockNumber;

    @Lob
    @Column(name = "book_image")
    private byte[] bookImage;

    @Column(name = "book_image_content_type")
    private String bookImageContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Book title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public Book author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public Book publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public Book publicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Language getLanguage() {
        return language;
    }

    public Book language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public BookCategory getCategory() {
        return category;
    }

    public Book category(BookCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public Book numberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
        return this;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public BookFormat getFormat() {
        return format;
    }

    public Book format(BookFormat format) {
        this.format = format;
        return this;
    }

    public void setFormat(BookFormat format) {
        this.format = format;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public Book isbn(Integer isbn) {
        this.isbn = isbn;
        return this;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public Double getShippingWeight() {
        return shippingWeight;
    }

    public Book shippingWeight(Double shippingWeight) {
        this.shippingWeight = shippingWeight;
        return this;
    }

    public void setShippingWeight(Double shippingWeight) {
        this.shippingWeight = shippingWeight;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public Book lastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
        return this;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Double getOurPrice() {
        return ourPrice;
    }

    public Book ourPrice(Double ourPrice) {
        this.ourPrice = ourPrice;
        return this;
    }

    public void setOurPrice(Double ourPrice) {
        this.ourPrice = ourPrice;
    }

    public Boolean isActive() {
        return active;
    }

    public Book active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public Book description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInStockNumber() {
        return inStockNumber;
    }

    public Book inStockNumber(Integer inStockNumber) {
        this.inStockNumber = inStockNumber;
        return this;
    }

    public void setInStockNumber(Integer inStockNumber) {
        this.inStockNumber = inStockNumber;
    }

    public byte[] getBookImage() {
        return bookImage;
    }

    public Book bookImage(byte[] bookImage) {
        this.bookImage = bookImage;
        return this;
    }

    public void setBookImage(byte[] bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookImageContentType() {
        return bookImageContentType;
    }

    public Book bookImageContentType(String bookImageContentType) {
        this.bookImageContentType = bookImageContentType;
        return this;
    }

    public void setBookImageContentType(String bookImageContentType) {
        this.bookImageContentType = bookImageContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        if (book.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", author='" + getAuthor() + "'" +
            ", publisher='" + getPublisher() + "'" +
            ", publicationDate='" + getPublicationDate() + "'" +
            ", language='" + getLanguage() + "'" +
            ", category='" + getCategory() + "'" +
            ", numberOfPages=" + getNumberOfPages() +
            ", format='" + getFormat() + "'" +
            ", isbn=" + getIsbn() +
            ", shippingWeight=" + getShippingWeight() +
            ", lastPrice=" + getLastPrice() +
            ", ourPrice=" + getOurPrice() +
            ", active='" + isActive() + "'" +
            ", description='" + getDescription() + "'" +
            ", inStockNumber=" + getInStockNumber() +
            ", bookImage='" + getBookImage() + "'" +
            ", bookImageContentType='" + getBookImageContentType() + "'" +
            "}";
    }
}
