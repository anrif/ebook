package com.acumen.article.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserBilling.
 */
@Entity
@Table(name = "user_billing")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userbilling")
public class UserBilling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_billing_name", nullable = false)
    private String userBillingName;

    @Column(name = "user_billing_street_1")
    private String userBillingStreet1;

    @Column(name = "user_billing_street_2")
    private String userBillingStreet2;

    @NotNull
    @Column(name = "user_billing_city", nullable = false)
    private String userBillingCity;

    @NotNull
    @Column(name = "user_billing_state", nullable = false)
    private String userBillingState;

    @NotNull
    @Column(name = "user_billing_country", nullable = false)
    private String userBillingCountry;

    @Column(name = "user_billing_zipcode")
    private String userBillingZipcode;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserBillingName() {
        return userBillingName;
    }

    public UserBilling userBillingName(String userBillingName) {
        this.userBillingName = userBillingName;
        return this;
    }

    public void setUserBillingName(String userBillingName) {
        this.userBillingName = userBillingName;
    }

    public String getUserBillingStreet1() {
        return userBillingStreet1;
    }

    public UserBilling userBillingStreet1(String userBillingStreet1) {
        this.userBillingStreet1 = userBillingStreet1;
        return this;
    }

    public void setUserBillingStreet1(String userBillingStreet1) {
        this.userBillingStreet1 = userBillingStreet1;
    }

    public String getUserBillingStreet2() {
        return userBillingStreet2;
    }

    public UserBilling userBillingStreet2(String userBillingStreet2) {
        this.userBillingStreet2 = userBillingStreet2;
        return this;
    }

    public void setUserBillingStreet2(String userBillingStreet2) {
        this.userBillingStreet2 = userBillingStreet2;
    }

    public String getUserBillingCity() {
        return userBillingCity;
    }

    public UserBilling userBillingCity(String userBillingCity) {
        this.userBillingCity = userBillingCity;
        return this;
    }

    public void setUserBillingCity(String userBillingCity) {
        this.userBillingCity = userBillingCity;
    }

    public String getUserBillingState() {
        return userBillingState;
    }

    public UserBilling userBillingState(String userBillingState) {
        this.userBillingState = userBillingState;
        return this;
    }

    public void setUserBillingState(String userBillingState) {
        this.userBillingState = userBillingState;
    }

    public String getUserBillingCountry() {
        return userBillingCountry;
    }

    public UserBilling userBillingCountry(String userBillingCountry) {
        this.userBillingCountry = userBillingCountry;
        return this;
    }

    public void setUserBillingCountry(String userBillingCountry) {
        this.userBillingCountry = userBillingCountry;
    }

    public String getUserBillingZipcode() {
        return userBillingZipcode;
    }

    public UserBilling userBillingZipcode(String userBillingZipcode) {
        this.userBillingZipcode = userBillingZipcode;
        return this;
    }

    public void setUserBillingZipcode(String userBillingZipcode) {
        this.userBillingZipcode = userBillingZipcode;
    }

    public User getUser() {
        return user;
    }

    public UserBilling user(User user) {
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
        UserBilling userBilling = (UserBilling) o;
        if (userBilling.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userBilling.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserBilling{" +
            "id=" + getId() +
            ", userBillingName='" + getUserBillingName() + "'" +
            ", userBillingStreet1='" + getUserBillingStreet1() + "'" +
            ", userBillingStreet2='" + getUserBillingStreet2() + "'" +
            ", userBillingCity='" + getUserBillingCity() + "'" +
            ", userBillingState='" + getUserBillingState() + "'" +
            ", userBillingCountry='" + getUserBillingCountry() + "'" +
            ", userBillingZipcode='" + getUserBillingZipcode() + "'" +
            "}";
    }
}
