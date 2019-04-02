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

import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the Ordered entity. This class is used in OrderedResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /ordereds?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrderedCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter orderDate;

    private LocalDateFilter shippingDate;

    private StringFilter shippingMethod;

    private StringFilter orderStatus;

    private BigDecimalFilter orderTotal;

    private LongFilter shippingAddresssId;

    private LongFilter billingAddresssId;

    private LongFilter paymentId;

    public OrderedCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateFilter orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateFilter getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDateFilter shippingDate) {
        this.shippingDate = shippingDate;
    }

    public StringFilter getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(StringFilter shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public StringFilter getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(StringFilter orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimalFilter getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimalFilter orderTotal) {
        this.orderTotal = orderTotal;
    }

    public LongFilter getShippingAddresssId() {
        return shippingAddresssId;
    }

    public void setShippingAddresssId(LongFilter shippingAddresssId) {
        this.shippingAddresssId = shippingAddresssId;
    }

    public LongFilter getBillingAddresssId() {
        return billingAddresssId;
    }

    public void setBillingAddresssId(LongFilter billingAddresssId) {
        this.billingAddresssId = billingAddresssId;
    }

    public LongFilter getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(LongFilter paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public String toString() {
        return "OrderedCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orderDate != null ? "orderDate=" + orderDate + ", " : "") +
                (shippingDate != null ? "shippingDate=" + shippingDate + ", " : "") +
                (shippingMethod != null ? "shippingMethod=" + shippingMethod + ", " : "") +
                (orderStatus != null ? "orderStatus=" + orderStatus + ", " : "") +
                (orderTotal != null ? "orderTotal=" + orderTotal + ", " : "") +
                (shippingAddresssId != null ? "shippingAddresssId=" + shippingAddresssId + ", " : "") +
                (billingAddresssId != null ? "billingAddresssId=" + billingAddresssId + ", " : "") +
                (paymentId != null ? "paymentId=" + paymentId + ", " : "") +
            "}";
    }

}
