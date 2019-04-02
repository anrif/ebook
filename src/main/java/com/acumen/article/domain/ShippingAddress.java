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
 * A ShippingAddress.
 */
@Entity
@Table(name = "shipping_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "shippingaddress")
public class ShippingAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "shipping_address_name", nullable = false)
    private String shippingAddressName;

    @Column(name = "shipping_address_street_1")
    private String shippingAddressStreet1;

    @Column(name = "shipping_address_street_2")
    private String shippingAddressStreet2;

    @NotNull
    @Column(name = "shipping_address_city", nullable = false)
    private String shippingAddressCity;

    @NotNull
    @Column(name = "shipping_address_state", nullable = false)
    private String shippingAddressState;

    @NotNull
    @Column(name = "shipping_address_country", nullable = false)
    private String shippingAddressCountry;

    @Column(name = "shipping_address_zipcode")
    private String shippingAddressZipcode;

    @ManyToMany(mappedBy = "shippingAddressses")
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

    public String getShippingAddressName() {
        return shippingAddressName;
    }

    public ShippingAddress shippingAddressName(String shippingAddressName) {
        this.shippingAddressName = shippingAddressName;
        return this;
    }

    public void setShippingAddressName(String shippingAddressName) {
        this.shippingAddressName = shippingAddressName;
    }

    public String getShippingAddressStreet1() {
        return shippingAddressStreet1;
    }

    public ShippingAddress shippingAddressStreet1(String shippingAddressStreet1) {
        this.shippingAddressStreet1 = shippingAddressStreet1;
        return this;
    }

    public void setShippingAddressStreet1(String shippingAddressStreet1) {
        this.shippingAddressStreet1 = shippingAddressStreet1;
    }

    public String getShippingAddressStreet2() {
        return shippingAddressStreet2;
    }

    public ShippingAddress shippingAddressStreet2(String shippingAddressStreet2) {
        this.shippingAddressStreet2 = shippingAddressStreet2;
        return this;
    }

    public void setShippingAddressStreet2(String shippingAddressStreet2) {
        this.shippingAddressStreet2 = shippingAddressStreet2;
    }

    public String getShippingAddressCity() {
        return shippingAddressCity;
    }

    public ShippingAddress shippingAddressCity(String shippingAddressCity) {
        this.shippingAddressCity = shippingAddressCity;
        return this;
    }

    public void setShippingAddressCity(String shippingAddressCity) {
        this.shippingAddressCity = shippingAddressCity;
    }

    public String getShippingAddressState() {
        return shippingAddressState;
    }

    public ShippingAddress shippingAddressState(String shippingAddressState) {
        this.shippingAddressState = shippingAddressState;
        return this;
    }

    public void setShippingAddressState(String shippingAddressState) {
        this.shippingAddressState = shippingAddressState;
    }

    public String getShippingAddressCountry() {
        return shippingAddressCountry;
    }

    public ShippingAddress shippingAddressCountry(String shippingAddressCountry) {
        this.shippingAddressCountry = shippingAddressCountry;
        return this;
    }

    public void setShippingAddressCountry(String shippingAddressCountry) {
        this.shippingAddressCountry = shippingAddressCountry;
    }

    public String getShippingAddressZipcode() {
        return shippingAddressZipcode;
    }

    public ShippingAddress shippingAddressZipcode(String shippingAddressZipcode) {
        this.shippingAddressZipcode = shippingAddressZipcode;
        return this;
    }

    public void setShippingAddressZipcode(String shippingAddressZipcode) {
        this.shippingAddressZipcode = shippingAddressZipcode;
    }

    public Set<Ordered> getOrdereds() {
        return ordereds;
    }

    public ShippingAddress ordereds(Set<Ordered> ordereds) {
        this.ordereds = ordereds;
        return this;
    }

    public ShippingAddress addOrdered(Ordered ordered) {
        this.ordereds.add(ordered);
        ordered.getShippingAddressses().add(this);
        return this;
    }

    public ShippingAddress removeOrdered(Ordered ordered) {
        this.ordereds.remove(ordered);
        ordered.getShippingAddressses().remove(this);
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
        ShippingAddress shippingAddress = (ShippingAddress) o;
        if (shippingAddress.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shippingAddress.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShippingAddress{" +
            "id=" + getId() +
            ", shippingAddressName='" + getShippingAddressName() + "'" +
            ", shippingAddressStreet1='" + getShippingAddressStreet1() + "'" +
            ", shippingAddressStreet2='" + getShippingAddressStreet2() + "'" +
            ", shippingAddressCity='" + getShippingAddressCity() + "'" +
            ", shippingAddressState='" + getShippingAddressState() + "'" +
            ", shippingAddressCountry='" + getShippingAddressCountry() + "'" +
            ", shippingAddressZipcode='" + getShippingAddressZipcode() + "'" +
            "}";
    }
}
