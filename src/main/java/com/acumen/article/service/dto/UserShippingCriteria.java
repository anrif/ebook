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
 * Criteria class for the UserShipping entity. This class is used in UserShippingResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /user-shippings?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserShippingCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter userShippingName;

    private StringFilter userShippingStreet1;

    private StringFilter userShippingStreet2;

    private StringFilter userShippingCity;

    private StringFilter userShippingState;

    private StringFilter userShippingCountry;

    private StringFilter userShippingZipcode;

    private BooleanFilter userShippingDefault;

    private LongFilter userId;

    public UserShippingCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUserShippingName() {
        return userShippingName;
    }

    public void setUserShippingName(StringFilter userShippingName) {
        this.userShippingName = userShippingName;
    }

    public StringFilter getUserShippingStreet1() {
        return userShippingStreet1;
    }

    public void setUserShippingStreet1(StringFilter userShippingStreet1) {
        this.userShippingStreet1 = userShippingStreet1;
    }

    public StringFilter getUserShippingStreet2() {
        return userShippingStreet2;
    }

    public void setUserShippingStreet2(StringFilter userShippingStreet2) {
        this.userShippingStreet2 = userShippingStreet2;
    }

    public StringFilter getUserShippingCity() {
        return userShippingCity;
    }

    public void setUserShippingCity(StringFilter userShippingCity) {
        this.userShippingCity = userShippingCity;
    }

    public StringFilter getUserShippingState() {
        return userShippingState;
    }

    public void setUserShippingState(StringFilter userShippingState) {
        this.userShippingState = userShippingState;
    }

    public StringFilter getUserShippingCountry() {
        return userShippingCountry;
    }

    public void setUserShippingCountry(StringFilter userShippingCountry) {
        this.userShippingCountry = userShippingCountry;
    }

    public StringFilter getUserShippingZipcode() {
        return userShippingZipcode;
    }

    public void setUserShippingZipcode(StringFilter userShippingZipcode) {
        this.userShippingZipcode = userShippingZipcode;
    }

    public BooleanFilter getUserShippingDefault() {
        return userShippingDefault;
    }

    public void setUserShippingDefault(BooleanFilter userShippingDefault) {
        this.userShippingDefault = userShippingDefault;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserShippingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userShippingName != null ? "userShippingName=" + userShippingName + ", " : "") +
                (userShippingStreet1 != null ? "userShippingStreet1=" + userShippingStreet1 + ", " : "") +
                (userShippingStreet2 != null ? "userShippingStreet2=" + userShippingStreet2 + ", " : "") +
                (userShippingCity != null ? "userShippingCity=" + userShippingCity + ", " : "") +
                (userShippingState != null ? "userShippingState=" + userShippingState + ", " : "") +
                (userShippingCountry != null ? "userShippingCountry=" + userShippingCountry + ", " : "") +
                (userShippingZipcode != null ? "userShippingZipcode=" + userShippingZipcode + ", " : "") +
                (userShippingDefault != null ? "userShippingDefault=" + userShippingDefault + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
