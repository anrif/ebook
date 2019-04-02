package com.acumen.article.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserShipping.
 */
@Entity
@Table(name = "user_shipping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "usershipping")
public class UserShipping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_shipping_name", nullable = false)
    private String userShippingName;

    @Column(name = "user_shipping_street_1")
    private String userShippingStreet1;

    @Column(name = "user_shipping_street_2")
    private String userShippingStreet2;

    @NotNull
    @Column(name = "user_shipping_city", nullable = false)
    private String userShippingCity;

    @NotNull
    @Column(name = "user_shipping_state", nullable = false)
    private String userShippingState;

    @NotNull
    @Column(name = "user_shipping_country", nullable = false)
    private String userShippingCountry;

    @NotNull
    @Column(name = "user_shipping_zipcode", nullable = false)
    private String userShippingZipcode;

    @Column(name = "user_shipping_default")
    private Boolean userShippingDefault;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserShippingName() {
        return userShippingName;
    }

    public UserShipping userShippingName(String userShippingName) {
        this.userShippingName = userShippingName;
        return this;
    }

    public void setUserShippingName(String userShippingName) {
        this.userShippingName = userShippingName;
    }

    public String getUserShippingStreet1() {
        return userShippingStreet1;
    }

    public UserShipping userShippingStreet1(String userShippingStreet1) {
        this.userShippingStreet1 = userShippingStreet1;
        return this;
    }

    public void setUserShippingStreet1(String userShippingStreet1) {
        this.userShippingStreet1 = userShippingStreet1;
    }

    public String getUserShippingStreet2() {
        return userShippingStreet2;
    }

    public UserShipping userShippingStreet2(String userShippingStreet2) {
        this.userShippingStreet2 = userShippingStreet2;
        return this;
    }

    public void setUserShippingStreet2(String userShippingStreet2) {
        this.userShippingStreet2 = userShippingStreet2;
    }

    public String getUserShippingCity() {
        return userShippingCity;
    }

    public UserShipping userShippingCity(String userShippingCity) {
        this.userShippingCity = userShippingCity;
        return this;
    }

    public void setUserShippingCity(String userShippingCity) {
        this.userShippingCity = userShippingCity;
    }

    public String getUserShippingState() {
        return userShippingState;
    }

    public UserShipping userShippingState(String userShippingState) {
        this.userShippingState = userShippingState;
        return this;
    }

    public void setUserShippingState(String userShippingState) {
        this.userShippingState = userShippingState;
    }

    public String getUserShippingCountry() {
        return userShippingCountry;
    }

    public UserShipping userShippingCountry(String userShippingCountry) {
        this.userShippingCountry = userShippingCountry;
        return this;
    }

    public void setUserShippingCountry(String userShippingCountry) {
        this.userShippingCountry = userShippingCountry;
    }

    public String getUserShippingZipcode() {
        return userShippingZipcode;
    }

    public UserShipping userShippingZipcode(String userShippingZipcode) {
        this.userShippingZipcode = userShippingZipcode;
        return this;
    }

    public void setUserShippingZipcode(String userShippingZipcode) {
        this.userShippingZipcode = userShippingZipcode;
    }

    public Boolean isUserShippingDefault() {
        return userShippingDefault;
    }

    public UserShipping userShippingDefault(Boolean userShippingDefault) {
        this.userShippingDefault = userShippingDefault;
        return this;
    }

    public void setUserShippingDefault(Boolean userShippingDefault) {
        this.userShippingDefault = userShippingDefault;
    }

    public User getUser() {
        return user;
    }

    public UserShipping user(User user) {
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
        UserShipping userShipping = (UserShipping) o;
        if (userShipping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userShipping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserShipping{" +
            "id=" + getId() +
            ", userShippingName='" + getUserShippingName() + "'" +
            ", userShippingStreet1='" + getUserShippingStreet1() + "'" +
            ", userShippingStreet2='" + getUserShippingStreet2() + "'" +
            ", userShippingCity='" + getUserShippingCity() + "'" +
            ", userShippingState='" + getUserShippingState() + "'" +
            ", userShippingCountry='" + getUserShippingCountry() + "'" +
            ", userShippingZipcode='" + getUserShippingZipcode() + "'" +
            ", userShippingDefault='" + isUserShippingDefault() + "'" +
            "}";
    }
}
