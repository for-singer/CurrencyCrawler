package com.oshurpik.repository;

import com.oshurpik.entity.Rate;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepositoryCustom {
    List<Rate> findByFromNameToName(String fromCurrencyName, String toCurrencyName);
}
