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
 * Criteria class for the UserBilling entity. This class is used in UserBillingResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /user-billings?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserBillingCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter userBillingName;

    private StringFilter userBillingStreet1;

    private StringFilter userBillingStreet2;

    private StringFilter userBillingCity;

    private StringFilter userBillingState;

    private StringFilter userBillingCountry;

    private StringFilter userBillingZipcode;

    private LongFilter userId;

    public UserBillingCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUserBillingName() {
        return userBillingName;
    }

    public void setUserBillingName(StringFilter userBillingName) {
        this.userBillingName = userBillingName;
    }

    public StringFilter getUserBillingStreet1() {
        return userBillingStreet1;
    }

    public void setUserBillingStreet1(StringFilter userBillingStreet1) {
        this.userBillingStreet1 = userBillingStreet1;
    }

    public StringFilter getUserBillingStreet2() {
        return userBillingStreet2;
    }

    public void setUserBillingStreet2(StringFilter userBillingStreet2) {
        this.userBillingStreet2 = userBillingStreet2;
    }

    public StringFilter getUserBillingCity() {
        return userBillingCity;
    }

    public void setUserBillingCity(StringFilter userBillingCity) {
        this.userBillingCity = userBillingCity;
    }

    public StringFilter getUserBillingState() {
        return userBillingState;
    }

    public void setUserBillingState(StringFilter userBillingState) {
        this.userBillingState = userBillingState;
    }

    public StringFilter getUserBillingCountry() {
        return userBillingCountry;
    }

    public void setUserBillingCountry(StringFilter userBillingCountry) {
        this.userBillingCountry = userBillingCountry;
    }

    public StringFilter getUserBillingZipcode() {
        return userBillingZipcode;
    }

    public void setUserBillingZipcode(StringFilter userBillingZipcode) {
        this.userBillingZipcode = userBillingZipcode;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserBillingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userBillingName != null ? "userBillingName=" + userBillingName + ", " : "") +
                (userBillingStreet1 != null ? "userBillingStreet1=" + userBillingStreet1 + ", " : "") +
                (userBillingStreet2 != null ? "userBillingStreet2=" + userBillingStreet2 + ", " : "") +
                (userBillingCity != null ? "userBillingCity=" + userBillingCity + ", " : "") +
                (userBillingState != null ? "userBillingState=" + userBillingState + ", " : "") +
                (userBillingCountry != null ? "userBillingCountry=" + userBillingCountry + ", " : "") +
                (userBillingZipcode != null ? "userBillingZipcode=" + userBillingZipcode + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
