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
 * Criteria class for the Payment entity. This class is used in PaymentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /payments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter type;

    private StringFilter cartName;

    private StringFilter cardNumber;

    private IntegerFilter expiryMonth;

    private IntegerFilter expiryYear;

    private IntegerFilter cvc;

    private StringFilter holderName;

    private LongFilter orderedId;

    public PaymentCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getCartName() {
        return cartName;
    }

    public void setCartName(StringFilter cartName) {
        this.cartName = cartName;
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

    public LongFilter getOrderedId() {
        return orderedId;
    }

    public void setOrderedId(LongFilter orderedId) {
        this.orderedId = orderedId;
    }

    @Override
    public String toString() {
        return "PaymentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (cartName != null ? "cartName=" + cartName + ", " : "") +
                (cardNumber != null ? "cardNumber=" + cardNumber + ", " : "") +
                (expiryMonth != null ? "expiryMonth=" + expiryMonth + ", " : "") +
                (expiryYear != null ? "expiryYear=" + expiryYear + ", " : "") +
                (cvc != null ? "cvc=" + cvc + ", " : "") +
                (holderName != null ? "holderName=" + holderName + ", " : "") +
                (orderedId != null ? "orderedId=" + orderedId + ", " : "") +
            "}";
    }

}
