package com.oshurpik.controller;

import com.oshurpik.helper.DateHelper;
import com.oshurpik.entity.Rate;
import com.oshurpik.service.RateService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CrawlerController {
    
    @Autowired
    RateService rateService;
    
    @Autowired
    DateHelper dateHelper;
    
    @RequestMapping(value="/", method=RequestMethod.GET)    
    @ResponseBody
    public List<Map<String, Object>> index() {        
        
        List<Rate> list = rateService.getActualRates();        
        
        List<Map<String, Object>> rateList = new ArrayList<>();
        for (Rate listItem : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("from", listItem.getFromCurrency().getName());
            map.put("to", listItem.getToCurrency().getName());
            map.put("rate", listItem.getRate());
            String date = dateHelper.toDate(listItem.getDate().getTime());
            map.put("date", date);
            map.put("suitable", listItem.isSuitable());
            rateList.add(map);
        }       
        
        return rateList;
    }
    
}
