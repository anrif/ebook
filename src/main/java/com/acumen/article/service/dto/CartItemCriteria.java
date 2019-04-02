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
 * Criteria class for the CartItem entity. This class is used in CartItemResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /cart-items?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CartItemCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter qty;

    private BigDecimalFilter subtotal;

    private LongFilter bookId;

    private LongFilter shoppingCartId;

    private LongFilter orderedId;

    public CartItemCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getQty() {
        return qty;
    }

    public void setQty(IntegerFilter qty) {
        this.qty = qty;
    }

    public BigDecimalFilter getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimalFilter subtotal) {
        this.subtotal = subtotal;
    }

    public LongFilter getBookId() {
        return bookId;
    }

    public void setBookId(LongFilter bookId) {
        this.bookId = bookId;
    }

    public LongFilter getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(LongFilter shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public LongFilter getOrderedId() {
        return orderedId;
    }

    public void setOrderedId(LongFilter orderedId) {
        this.orderedId = orderedId;
    }

    @Override
    public String toString() {
        return "CartItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (qty != null ? "qty=" + qty + ", " : "") +
                (subtotal != null ? "subtotal=" + subtotal + ", " : "") +
                (bookId != null ? "bookId=" + bookId + ", " : "") +
                (shoppingCartId != null ? "shoppingCartId=" + shoppingCartId + ", " : "") +
                (orderedId != null ? "orderedId=" + orderedId + ", " : "") +
            "}";
    }

}
