package com.oshurpik.repository.impl;

import com.oshurpik.entity.Rate;
import com.oshurpik.repository.RateRepositoryCustom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RateRepositoryImpl implements RateRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    
    public List<Rate> findByFromNameToName(String fromCurrencyName, String toCurrencyName) {
        List<Rate> list = em.createQuery("SELECT r " +
                                            "FROM Rate r " +
                                            "INNER JOIN r.fromCurrency c " +
                                            "INNER JOIN r.toCurrency c2 " +
                                            "WHERE " +
                                            "c.name = :fromCurrencyName AND " +
                                            "c2.name = :toCurrencyName " +
                                            "ORDER BY r.date DESC")
                                                .setParameter("fromCurrencyName", fromCurrencyName)
                                                .setParameter("toCurrencyName", toCurrencyName)
                                                .setMaxResults(1).getResultList();
        return list;
    }
}
