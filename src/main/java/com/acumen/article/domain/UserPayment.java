package com.acumen.article.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.acumen.article.domain.enumeration.TypePayment;

/**
 * A UserPayment.
 */
@Entity
@Table(name = "user_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userpayment")
public class UserPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private TypePayment type;

    @NotNull
    @Column(name = "card_name", nullable = false)
    private String cardName;

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

    @Column(name = "default_payment")
    private Boolean defaultPayment;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypePayment getType() {
        return type;
    }

    public UserPayment type(TypePayment type) {
        this.type = type;
        return this;
    }

    public void setType(TypePayment type) {
        this.type = type;
    }

    public String getCardName() {
        return cardName;
    }

    public UserPayment cardName(String cardName) {
        this.cardName = cardName;
        return this;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public UserPayment cardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public UserPayment expiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
        return this;
    }

    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public UserPayment expiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
        return this;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    public Integer getCvc() {
        return cvc;
    }

    public UserPayment cvc(Integer cvc) {
        this.cvc = cvc;
        return this;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    public String getHolderName() {
        return holderName;
    }

    public UserPayment holderName(String holderName) {
        this.holderName = holderName;
        return this;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Boolean isDefaultPayment() {
        return defaultPayment;
    }

    public UserPayment defaultPayment(Boolean defaultPayment) {
        this.defaultPayment = defaultPayment;
        return this;
    }

    public void setDefaultPayment(Boolean defaultPayment) {
        this.defaultPayment = defaultPayment;
    }

    public User getUser() {
        return user;
    }

    public UserPayment user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        UserPayment userPayment = (UserPayment) o;
        if (userPayment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userPayment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPayment{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", cardName='" + getCardName() + "'" +
            ", cardNumber='" + getCardNumber() + "'" +
            ", expiryMonth=" + getExpiryMonth() +
            ", expiryYear=" + getExpiryYear() +
            ", cvc=" + getCvc() +
            ", holderName='" + getHolderName() + "'" +
            ", defaultPayment='" + isDefaultPayment() + "'" +
            "}";
    }
}
