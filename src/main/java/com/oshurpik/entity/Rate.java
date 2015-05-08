package com.oshurpik.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="RATE")
public class Rate implements Serializable {

    private Integer id;
    private Currency fromCurrency;
    private Currency toCurrency;
    private Float rate;
    private Date date;
    private boolean suitable;

    public Rate() {
    }

    public Rate(Currency fromCurrency, Currency toCurrency, Float rate, Date date, boolean suitable) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
        this.date = date;
        this.suitable = suitable;
    }
    
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "FROM_CURRENCY_ID", nullable = false)
    public Currency getFromCurrency() {
        return this.fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }
    
    @ManyToOne
    @JoinColumn(name = "TO_CURRENCY_ID", nullable = false)
    public Currency getToCurrency() {
        return this.toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    @Column(name="RATE", nullable = false)
    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
    
    @Column(name="DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name="SUITABLE", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    public boolean isSuitable() {
        return suitable;
    }

    public void setSuitable(boolean suitable) {
        this.suitable = suitable;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.fromCurrency);
        hash = 59 * hash + Objects.hashCode(this.toCurrency);
        hash = 59 * hash + Objects.hashCode(this.rate);
        hash = 59 * hash + Objects.hashCode(this.date);
        hash = 59 * hash + (this.suitable ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rate other = (Rate) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fromCurrency, other.fromCurrency)) {
            return false;
        }
        if (!Objects.equals(this.toCurrency, other.toCurrency)) {
            return false;
        }
        if (!Objects.equals(this.rate, other.rate)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (this.suitable != other.suitable) {
            return false;
        }
        return true;
    }
    
    
    
    
}


