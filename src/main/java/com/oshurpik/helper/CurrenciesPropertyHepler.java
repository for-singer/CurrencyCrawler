package com.oshurpik.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.springframework.stereotype.Component;

@Component
public class CurrenciesPropertyHepler {
    private final String SETTINGS_FILE = "crawling.properties";
    
    private final String CURRENCIES_URL = "currencies.url";
    private final String CURRENCIES_CRAWLER_PERIODICITY = "currencies.crawler.periodicity";
    private final String CURRENCIES_PAIRS = "currencies.pairs";
    private final String CURRENCIES_HANDLER_MAXLENGTH = "currencies.handler.maxlength";
    
    private final String CURRENCIES_PAIRS_SPLITTER = ";";
    private final String CURRENCIES_PAIRS_CURRENCIES_SPLITTER = ",";
    
    private String getPropertyValue(String valueName) {
        Properties prop = new Properties();
	InputStream input = null;
 
	try {
            input = this.getClass().getClassLoader().getResourceAsStream(SETTINGS_FILE);
            prop.load(input);
            return prop.getProperty(valueName);
	} 
        catch (IOException ex) {
		ex.printStackTrace();
	} 
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
	}
        return null;
    }
    
    public String getCurrenciesUrl() {
        return getPropertyValue(CURRENCIES_URL);
    }
    
    public Integer getCurrenciesCrawlerPeriodicity() {
        return Integer.parseInt(getPropertyValue(CURRENCIES_CRAWLER_PERIODICITY));
    }
    
    public List<FromCurrToCurr> getCurrenciesPairs() {
        List<String> list = Arrays.asList(getPropertyValue(CURRENCIES_PAIRS).split(CURRENCIES_PAIRS_SPLITTER));
        List<FromCurrToCurr> result = new ArrayList<>();
        for (String item : list) {
            String[] itemArray = item.split(CURRENCIES_PAIRS_CURRENCIES_SPLITTER);
            result.add(new FromCurrToCurr(itemArray[0], itemArray[1]));
        }
        return result;
    }
    
    public Integer getCurrenciesHandlerMaxLength() {
        return Integer.parseInt(getPropertyValue(CURRENCIES_HANDLER_MAXLENGTH));
    }
}
