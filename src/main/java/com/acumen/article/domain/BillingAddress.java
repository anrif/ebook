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
 * A BillingAddress.
 */
@Entity
@Table(name = "billing_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "billingaddress")
public class BillingAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "billing_address_name", nullable = false)
    private String billingAddressName;

    @Column(name = "billing_address_street_1")
    private String billingAddressStreet1;

    @Column(name = "billing_address_street_2")
    private String billingAddressStreet2;

    @NotNull
    @Column(name = "billing_address_city", nullable = false)
    private String billingAddressCity;

    @NotNull
    @Column(name = "billing_address_state", nullable = false)
    private String billingAddressState;

    @NotNull
    @Column(name = "billing_address_country", nullable = false)
    private String billingAddressCountry;

    @Column(name = "billing_address_zipcode")
    private String billingAddressZipcode;

    @ManyToMany(mappedBy = "billingAddressses")
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

    public String getBillingAddressName() {
        return billingAddressName;
    }

    public BillingAddress billingAddressName(String billingAddressName) {
        this.billingAddressName = billingAddressName;
        return this;
    }

    public void setBillingAddressName(String billingAddressName) {
        this.billingAddressName = billingAddressName;
    }

    public String getBillingAddressStreet1() {
        return billingAddressStreet1;
    }

    public BillingAddress billingAddressStreet1(String billingAddressStreet1) {
        this.billingAddressStreet1 = billingAddressStreet1;
        return this;
    }

    public void setBillingAddressStreet1(String billingAddressStreet1) {
        this.billingAddressStreet1 = billingAddressStreet1;
    }

    public String getBillingAddressStreet2() {
        return billingAddressStreet2;
    }

    public BillingAddress billingAddressStreet2(String billingAddressStreet2) {
        this.billingAddressStreet2 = billingAddressStreet2;
        return this;
    }

    public void setBillingAddressStreet2(String billingAddressStreet2) {
        this.billingAddressStreet2 = billingAddressStreet2;
    }

    public String getBillingAddressCity() {
        return billingAddressCity;
    }

    public BillingAddress billingAddressCity(String billingAddressCity) {
        this.billingAddressCity = billingAddressCity;
        return this;
    }

    public void setBillingAddressCity(String billingAddressCity) {
        this.billingAddressCity = billingAddressCity;
    }

    public String getBillingAddressState() {
        return billingAddressState;
    }

    public BillingAddress billingAddressState(String billingAddressState) {
        this.billingAddressState = billingAddressState;
        return this;
    }

    public void setBillingAddressState(String billingAddressState) {
        this.billingAddressState = billingAddressState;
    }

    public String getBillingAddressCountry() {
        return billingAddressCountry;
    }

    public BillingAddress billingAddressCountry(String billingAddressCountry) {
        this.billingAddressCountry = billingAddressCountry;
        return this;
    }

    public void setBillingAddressCountry(String billingAddressCountry) {
        this.billingAddressCountry = billingAddressCountry;
    }

    public String getBillingAddressZipcode() {
        return billingAddressZipcode;
    }

    public BillingAddress billingAddressZipcode(String billingAddressZipcode) {
        this.billingAddressZipcode = billingAddressZipcode;
        return this;
    }

    public void setBillingAddressZipcode(String billingAddressZipcode) {
        this.billingAddressZipcode = billingAddressZipcode;
    }

    public Set<Ordered> getOrdereds() {
        return ordereds;
    }

    public BillingAddress ordereds(Set<Ordered> ordereds) {
        this.ordereds = ordereds;
        return this;
    }

    public BillingAddress addOrdered(Ordered ordered) {
        this.ordereds.add(ordered);
        ordered.getBillingAddressses().add(this);
        return this;
    }

    public BillingAddress removeOrdered(Ordered ordered) {
        this.ordereds.remove(ordered);
        ordered.getBillingAddressses().remove(this);
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
        BillingAddress billingAddress = (BillingAddress) o;
        if (billingAddress.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billingAddress.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillingAddress{" +
            "id=" + getId() +
            ", billingAddressName='" + getBillingAddressName() + "'" +
            ", billingAddressStreet1='" + getBillingAddressStreet1() + "'" +
            ", billingAddressStreet2='" + getBillingAddressStreet2() + "'" +
            ", billingAddressCity='" + getBillingAddressCity() + "'" +
            ", billingAddressState='" + getBillingAddressState() + "'" +
            ", billingAddressCountry='" + getBillingAddressCountry() + "'" +
            ", billingAddressZipcode='" + getBillingAddressZipcode() + "'" +
            "}";
    }
}
