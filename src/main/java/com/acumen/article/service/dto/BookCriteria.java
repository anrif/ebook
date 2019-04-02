package com.acumen.article.service.dto;

import java.io.Serializable;
import com.acumen.article.domain.enumeration.Language;
import com.acumen.article.domain.enumeration.BookCategory;
import com.acumen.article.domain.enumeration.BookFormat;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Book entity. This class is used in BookResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /books?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BookCriteria implements Serializable {
    /**
     * Class for filtering Language
     */
    public static class LanguageFilter extends Filter<Language> {
    }

    /**
     * Class for filtering BookCategory
     */
    public static class BookCategoryFilter extends Filter<BookCategory> {
    }

    /**
     * Class for filtering BookFormat
     */
    public static class BookFormatFilter extends Filter<BookFormat> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter title;

    private StringFilter author;

    private StringFilter publisher;

    private StringFilter publicationDate;

    private LanguageFilter language;

    private BookCategoryFilter category;

    private IntegerFilter numberOfPages;

    private BookFormatFilter format;

    private IntegerFilter isbn;

    private DoubleFilter shippingWeight;

    private DoubleFilter lastPrice;

    private DoubleFilter ourPrice;

    private BooleanFilter active;

    private IntegerFilter inStockNumber;

    public BookCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getAuthor() {
        return author;
    }

    public void setAuthor(StringFilter author) {
        this.author = author;
    }

    public StringFilter getPublisher() {
        return publisher;
    }

    public void setPublisher(StringFilter publisher) {
        this.publisher = publisher;
    }

    public StringFilter getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(StringFilter publicationDate) {
        this.publicationDate = publicationDate;
    }

    public LanguageFilter getLanguage() {
        return language;
    }

    public void setLanguage(LanguageFilter language) {
        this.language = language;
    }

    public BookCategoryFilter getCategory() {
        return category;
    }

    public void setCategory(BookCategoryFilter category) {
        this.category = category;
    }

    public IntegerFilter getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(IntegerFilter numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public BookFormatFilter getFormat() {
        return format;
    }

    public void setFormat(BookFormatFilter format) {
        this.format = format;
    }

    public IntegerFilter getIsbn() {
        return isbn;
    }

    public void setIsbn(IntegerFilter isbn) {
        this.isbn = isbn;
    }

    public DoubleFilter getShippingWeight() {
        return shippingWeight;
    }

    public void setShippingWeight(DoubleFilter shippingWeight) {
        this.shippingWeight = shippingWeight;
    }

    public DoubleFilter getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(DoubleFilter lastPrice) {
        this.lastPrice = lastPrice;
    }

    public DoubleFilter getOurPrice() {
        return ourPrice;
    }

    public void setOurPrice(DoubleFilter ourPrice) {
        this.ourPrice = ourPrice;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public IntegerFilter getInStockNumber() {
        return inStockNumber;
    }

    public void setInStockNumber(IntegerFilter inStockNumber) {
        this.inStockNumber = inStockNumber;
    }

    @Override
    public String toString() {
        return "BookCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (author != null ? "author=" + author + ", " : "") +
                (publisher != null ? "publisher=" + publisher + ", " : "") +
                (publicationDate != null ? "publicationDate=" + publicationDate + ", " : "") +
                (language != null ? "language=" + language + ", " : "") +
                (category != null ? "category=" + category + ", " : "") +
                (numberOfPages != null ? "numberOfPages=" + numberOfPages + ", " : "") +
                (format != null ? "format=" + format + ", " : "") +
                (isbn != null ? "isbn=" + isbn + ", " : "") +
                (shippingWeight != null ? "shippingWeight=" + shippingWeight + ", " : "") +
                (lastPrice != null ? "lastPrice=" + lastPrice + ", " : "") +
                (ourPrice != null ? "ourPrice=" + ourPrice + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (inStockNumber != null ? "inStockNumber=" + inStockNumber + ", " : "") +
            "}";
    }

}
