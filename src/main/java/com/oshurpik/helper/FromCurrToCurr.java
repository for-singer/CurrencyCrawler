package com.oshurpik.helper;

import java.util.Objects;

public class FromCurrToCurr {
    private String fromCurrency;
    private String toCurrency;

    public FromCurrToCurr(String fromCurrency, String toCurrency) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.fromCurrency);
        hash = 97 * hash + Objects.hashCode(this.toCurrency);
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
        final FromCurrToCurr other = (FromCurrToCurr) obj;
        if (!Objects.equals(this.fromCurrency, other.fromCurrency)) {
            return false;
        }
        if (!Objects.equals(this.toCurrency, other.toCurrency)) {
            return false;
        }
        return true;
    }
    
    
}
