package com.acumen.article.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;





/**
 * Criteria class for the ShoppingCart entity. This class is used in ShoppingCartResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /shopping-carts?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ShoppingCartCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private BigDecimalFilter grandTotal;

    private LongFilter userId;

    public ShoppingCartCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimalFilter grandTotal) {
        this.grandTotal = grandTotal;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ShoppingCartCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (grandTotal != null ? "grandTotal=" + grandTotal + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
