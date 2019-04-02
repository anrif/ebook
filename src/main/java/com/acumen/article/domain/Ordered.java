package com.acumen.article.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ordered.
 */
@Entity
@Table(name = "ordered")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ordered")
public class Ordered implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @NotNull
    @Column(name = "shipping_date", nullable = false)
    private LocalDate shippingDate;

    @NotNull
    @Column(name = "shipping_method", nullable = false)
    private String shippingMethod;

    @NotNull
    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @NotNull
    @Column(name = "order_total", precision=10, scale=2, nullable = false)
    private BigDecimal orderTotal;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ordered_shipping_addresss",
               joinColumns = @JoinColumn(name="ordereds_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="shipping_addressses_id", referencedColumnName="id"))
    private Set<ShippingAddress> shippingAddressses = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ordered_billing_addresss",
               joinColumns = @JoinColumn(name="ordereds_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="billing_addressses_id", referencedColumnName="id"))
    private Set<BillingAddress> billingAddressses = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ordered_payment",
               joinColumns = @JoinColumn(name="ordereds_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="payments_id", referencedColumnName="id"))
    private Set<Payment> payments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Ordered orderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public Ordered shippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
        return this;
    }

    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public Ordered shippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
        return this;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public Ordered orderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public Ordered orderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
        return this;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Set<ShippingAddress> getShippingAddressses() {
        return shippingAddressses;
    }

    public Ordered shippingAddressses(Set<ShippingAddress> shippingAddresses) {
        this.shippingAddressses = shippingAddresses;
        return this;
    }

    public Ordered addShippingAddresss(ShippingAddress shippingAddress) {
        this.shippingAddressses.add(shippingAddress);
        shippingAddress.getOrdereds().add(this);
        return this;
    }

    public Ordered removeShippingAddresss(ShippingAddress shippingAddress) {
        this.shippingAddressses.remove(shippingAddress);
        shippingAddress.getOrdereds().remove(this);
        return this;
    }

    public void setShippingAddressses(Set<ShippingAddress> shippingAddresses) {
        this.shippingAddressses = shippingAddresses;
    }

    public Set<BillingAddress> getBillingAddressses() {
        return billingAddressses;
    }

    public Ordered billingAddressses(Set<BillingAddress> billingAddresses) {
        this.billingAddressses = billingAddresses;
        return this;
    }

    public Ordered addBillingAddresss(BillingAddress billingAddress) {
        this.billingAddressses.add(billingAddress);
        billingAddress.getOrdereds().add(this);
        return this;
    }

    public Ordered removeBillingAddresss(BillingAddress billingAddress) {
        this.billingAddressses.remove(billingAddress);
        billingAddress.getOrdereds().remove(this);
        return this;
    }

    public void setBillingAddressses(Set<BillingAddress> billingAddresses) {
        this.billingAddressses = billingAddresses;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public Ordered payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public Ordered addPayment(Payment payment) {
        this.payments.add(payment);
        payment.getOrdereds().add(this);
        return this;
    }

    public Ordered removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.getOrdereds().remove(this);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
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
        Ordered ordered = (Ordered) o;
        if (ordered.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordered.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ordered{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", shippingDate='" + getShippingDate() + "'" +
            ", shippingMethod='" + getShippingMethod() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", orderTotal=" + getOrderTotal() +
            "}";
    }
}
