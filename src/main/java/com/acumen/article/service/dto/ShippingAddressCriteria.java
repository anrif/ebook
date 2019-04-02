package com.acumen.article.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the ShippingAddress entity. This class is used in ShippingAddressResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /shipping-addresses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ShippingAddressCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter shippingAddressName;

    private StringFilter shippingAddressStreet1;

    private StringFilter shippingAddressStreet2;

    private StringFilter shippingAddressCity;

    private StringFilter shippingAddressState;

    private StringFilter shippingAddressCountry;

    private StringFilter shippingAddressZipcode;

    private LongFilter orderedId;

    public ShippingAddressCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getShippingAddressName() {
        return shippingAddressName;
    }

    public void setShippingAddressName(StringFilter shippingAddressName) {
        this.shippingAddressName = shippingAddressName;
    }

    public StringFilter getShippingAddressStreet1() {
        return shippingAddressStreet1;
    }

    public void setShippingAddressStreet1(StringFilter shippingAddressStreet1) {
        this.shippingAddressStreet1 = shippingAddressStreet1;
    }

    public StringFilter getShippingAddressStreet2() {
        return shippingAddressStreet2;
    }

    public void setShippingAddressStreet2(StringFilter shippingAddressStreet2) {
        this.shippingAddressStreet2 = shippingAddressStreet2;
    }

    public StringFilter getShippingAddressCity() {
        return shippingAddressCity;
    }

    public void setShippingAddressCity(StringFilter shippingAddressCity) {
        this.shippingAddressCity = shippingAddressCity;
    }

    public StringFilter getShippingAddressState() {
        return shippingAddressState;
    }

    public void setShippingAddressState(StringFilter shippingAddressState) {
        this.shippingAddressState = shippingAddressState;
    }

    public StringFilter getShippingAddressCountry() {
        return shippingAddressCountry;
    }

    public void setShippingAddressCountry(StringFilter shippingAddressCountry) {
        this.shippingAddressCountry = shippingAddressCountry;
    }

    public StringFilter getShippingAddressZipcode() {
        return shippingAddressZipcode;
    }

    public void setShippingAddressZipcode(StringFilter shippingAddressZipcode) {
        this.shippingAddressZipcode = shippingAddressZipcode;
    }

    public LongFilter getOrderedId() {
        return orderedId;
    }

    public void setOrderedId(LongFilter orderedId) {
        this.orderedId = orderedId;
    }

    @Override
    public String toString() {
        return "ShippingAddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (shippingAddressName != null ? "shippingAddressName=" + shippingAddressName + ", " : "") +
                (shippingAddressStreet1 != null ? "shippingAddressStreet1=" + shippingAddressStreet1 + ", " : "") +
                (shippingAddressStreet2 != null ? "shippingAddressStreet2=" + shippingAddressStreet2 + ", " : "") +
                (shippingAddressCity != null ? "shippingAddressCity=" + shippingAddressCity + ", " : "") +
                (shippingAddressState != null ? "shippingAddressState=" + shippingAddressState + ", " : "") +
                (shippingAddressCountry != null ? "shippingAddressCountry=" + shippingAddressCountry + ", " : "") +
                (shippingAddressZipcode != null ? "shippingAddressZipcode=" + shippingAddressZipcode + ", " : "") +
                (orderedId != null ? "orderedId=" + orderedId + ", " : "") +
            "}";
    }

}
