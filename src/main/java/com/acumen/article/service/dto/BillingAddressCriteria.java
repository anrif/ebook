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
 * Criteria class for the BillingAddress entity. This class is used in BillingAddressResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /billing-addresses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BillingAddressCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter billingAddressName;

    private StringFilter billingAddressStreet1;

    private StringFilter billingAddressStreet2;

    private StringFilter billingAddressCity;

    private StringFilter billingAddressState;

    private StringFilter billingAddressCountry;

    private StringFilter billingAddressZipcode;

    private LongFilter orderedId;

    public BillingAddressCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBillingAddressName() {
        return billingAddressName;
    }

    public void setBillingAddressName(StringFilter billingAddressName) {
        this.billingAddressName = billingAddressName;
    }

    public StringFilter getBillingAddressStreet1() {
        return billingAddressStreet1;
    }

    public void setBillingAddressStreet1(StringFilter billingAddressStreet1) {
        this.billingAddressStreet1 = billingAddressStreet1;
    }

    public StringFilter getBillingAddressStreet2() {
        return billingAddressStreet2;
    }

    public void setBillingAddressStreet2(StringFilter billingAddressStreet2) {
        this.billingAddressStreet2 = billingAddressStreet2;
    }

    public StringFilter getBillingAddressCity() {
        return billingAddressCity;
    }

    public void setBillingAddressCity(StringFilter billingAddressCity) {
        this.billingAddressCity = billingAddressCity;
    }

    public StringFilter getBillingAddressState() {
        return billingAddressState;
    }

    public void setBillingAddressState(StringFilter billingAddressState) {
        this.billingAddressState = billingAddressState;
    }

    public StringFilter getBillingAddressCountry() {
        return billingAddressCountry;
    }

    public void setBillingAddressCountry(StringFilter billingAddressCountry) {
        this.billingAddressCountry = billingAddressCountry;
    }

    public StringFilter getBillingAddressZipcode() {
        return billingAddressZipcode;
    }

    public void setBillingAddressZipcode(StringFilter billingAddressZipcode) {
        this.billingAddressZipcode = billingAddressZipcode;
    }

    public LongFilter getOrderedId() {
        return orderedId;
    }

    public void setOrderedId(LongFilter orderedId) {
        this.orderedId = orderedId;
    }

    @Override
    public String toString() {
        return "BillingAddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (billingAddressName != null ? "billingAddressName=" + billingAddressName + ", " : "") +
                (billingAddressStreet1 != null ? "billingAddressStreet1=" + billingAddressStreet1 + ", " : "") +
                (billingAddressStreet2 != null ? "billingAddressStreet2=" + billingAddressStreet2 + ", " : "") +
                (billingAddressCity != null ? "billingAddressCity=" + billingAddressCity + ", " : "") +
                (billingAddressState != null ? "billingAddressState=" + billingAddressState + ", " : "") +
                (billingAddressCountry != null ? "billingAddressCountry=" + billingAddressCountry + ", " : "") +
                (billingAddressZipcode != null ? "billingAddressZipcode=" + billingAddressZipcode + ", " : "") +
                (orderedId != null ? "orderedId=" + orderedId + ", " : "") +
            "}";
    }

}
