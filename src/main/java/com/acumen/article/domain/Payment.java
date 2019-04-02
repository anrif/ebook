package com.acumen.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "payment")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "cart_name", nullable = false)
    private String cartName;

    @NotNull
    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @NotNull
    @Column(name = "expiry_month", nullable = false)
    private Integer expiryMonth;

    @NotNull
    @Column(name = "expiry_year", nullable = false)
    private Integer expiryYear;

    @NotNull
    @Column(name = "cvc", nullable = false)
    private Integer cvc;

    @NotNull
    @Column(name = "holder_name", nullable = false)
    private String holderName;

    @ManyToMany(mappedBy = "payments")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ordered> ordereds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Payment type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCartName() {
        return cartName;
    }

    public Payment cartName(String cartName) {
        this.cartName = cartName;
        return this;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Payment cardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public Payment expiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
        return this;
    }

    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public Payment expiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
        return this;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    public Integer getCvc() {
        return cvc;
    }

    public Payment cvc(Integer cvc) {
        this.cvc = cvc;
        return this;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    public String getHolderName() {
        return holderName;
    }

    public Payment holderName(String holderName) {
        this.holderName = holderName;
        return this;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Set<Ordered> getOrdereds() {
        return ordereds;
    }

    public Payment ordereds(Set<Ordered> ordereds) {
        this.ordereds = ordereds;
        return this;
    }

    public Payment addOrdered(Ordered ordered) {
        this.ordereds.add(ordered);
        ordered.getPayments().add(this);
        return this;
    }

    public Payment removeOrdered(Ordered ordered) {
        this.ordereds.remove(ordered);
        ordered.getPayments().remove(this);
        return this;
    }

    public void setOrdereds(Set<Ordered> ordereds) {
        this.ordereds = ordereds;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Payment payment = (Payment) o;
        if (payment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", cartName='" + getCartName() + "'" +
            ", cardNumber='" + getCardNumber() + "'" +
            ", expiryMonth=" + getExpiryMonth() +
            ", expiryYear=" + getExpiryYear() +
            ", cvc=" + getCvc() +
            ", holderName='" + getHolderName() + "'" +
            "}";
    }
}
