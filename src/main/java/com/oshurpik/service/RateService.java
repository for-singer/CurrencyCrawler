package com.oshurpik.service;

import com.oshurpik.entity.Currency;
import com.oshurpik.entity.Rate;
import com.oshurpik.repository.CurrencyRepository;
import com.oshurpik.repository.RateRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RateService {
    
    @Autowired
    CurrencyRepository currencyRepository;
    
    @Autowired
    RateRepository rateRepository;
    
    public void addData(String fromCurrency, String toCurrency, Float rate) throws IOException {
        Currency fromCurrencyEntity = addCurrencyIfNotExist(fromCurrency);
        Currency toCurrencyEntity = addCurrencyIfNotExist(toCurrency);
                        
        List<Rate> currentRate = rateRepository.findByFromNameToName(fromCurrency, toCurrency);
        float currRate = 0f;
        if (currentRate.size() > 0) {
            currRate = currentRate.get(0).getRate();            
        }
        

        
        if (rate != currRate) {
            Rate newRate = new Rate(fromCurrencyEntity, toCurrencyEntity, rate, new Date(), false);
            rateRepository.save(newRate);
        }
        
    }
    
    public List<Rate> getActualRates() {
        return rateRepository.getActualRates();
    }
    
    public List<Integer> getActualRatesIds() {        
        List<Rate> rates = rateRepository.findAll();
        List<Integer> idList = new ArrayList<>();
        for(Rate item : rates) {
            idList.add(item.getFromCurrency().getId());
        }            

        Set<Integer> idSet = new HashSet<>();
        for (Integer idItem  : idList) {
            idSet.add(idItem);
        }
        idList = new ArrayList<>(idSet);

        return idList;
    }
    
    public Currency addCurrencyIfNotExist(String currency) {
        Currency currencyEntity = currencyRepository.findByName(currency);
        if (currencyEntity == null) {
            currencyEntity = new Currency(currency);
            currencyRepository.save(currencyEntity);
        }
        return currencyEntity;
    }
    
    public Integer getCurrencyIdByName(String name) {
        try {
            return currencyRepository.findByName(name).getId();
        }
        catch(Exception ex) {
            return null;
        }
    }
    
    public void updateActualRateSuitable(boolean suitable) {        
        List<Rate> rates = rateRepository.getActualRates();
        List<Integer> rateIdList = new ArrayList<>();
            
        for (Rate rateItem : rates) {
            rateIdList.add(rateItem.getId());
        }
        
        rateRepository.updateActualRateSuitable(suitable, rateIdList);
    }

}
