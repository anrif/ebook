package com.acumen.article.service.dto;

import java.io.Serializable;
import com.acumen.article.domain.enumeration.TypePayment;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the UserPayment entity. This class is used in UserPaymentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /user-payments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserPaymentCriteria implements Serializable {
    /**
     * Class for filtering TypePayment
     */
    public static class TypePaymentFilter extends Filter<TypePayment> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private TypePaymentFilter type;

    private StringFilter cardName;

    private StringFilter cardNumber;

    private IntegerFilter expiryMonth;

    private IntegerFilter expiryYear;

    private IntegerFilter cvc;

    private StringFilter holderName;

    private BooleanFilter defaultPayment;

    private LongFilter userId;

    public UserPaymentCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TypePaymentFilter getType() {
        return type;
    }

    public void setType(TypePaymentFilter type) {
        this.type = type;
    }

    public StringFilter getCardName() {
        return cardName;
    }

    public void setCardName(StringFilter cardName) {
        this.cardName = cardName;
    }

    public StringFilter getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(StringFilter cardNumber) {
        this.cardNumber = cardNumber;
    }

    public IntegerFilter getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(IntegerFilter expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public IntegerFilter getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(IntegerFilter expiryYear) {
        this.expiryYear = expiryYear;
    }

    public IntegerFilter getCvc() {
        return cvc;
    }

    public void setCvc(IntegerFilter cvc) {
        this.cvc = cvc;
    }

    public StringFilter getHolderName() {
        return holderName;
    }

    public void setHolderName(StringFilter holderName) {
        this.holderName = holderName;
    }

    public BooleanFilter getDefaultPayment() {
        return defaultPayment;
    }

    public void setDefaultPayment(BooleanFilter defaultPayment) {
        this.defaultPayment = defaultPayment;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserPaymentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (cardName != null ? "cardName=" + cardName + ", " : "") +
                (cardNumber != null ? "cardNumber=" + cardNumber + ", " : "") +
                (expiryMonth != null ? "expiryMonth=" + expiryMonth + ", " : "") +
                (expiryYear != null ? "expiryYear=" + expiryYear + ", " : "") +
                (cvc != null ? "cvc=" + cvc + ", " : "") +
                (holderName != null ? "holderName=" + holderName + ", " : "") +
                (defaultPayment != null ? "defaultPayment=" + defaultPayment + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
