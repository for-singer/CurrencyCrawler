package com.oshurpik.component.currencyhandler;

import com.oshurpik.helper.HttpRequestHelper;
import com.oshurpik.service.RateService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpRequestWorker implements Runnable {
        
    private final String url;
    private final String fromCurrency;
    private final String toCurrency;
    private final RateService rateService;
    
    public HttpRequestWorker(String url, String fromCurrency, String toCurrency, RateService rateService) {
        this.url = url;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rateService = rateService;
    }

    @Override
    public void run() {
        URI uri = UriBuilder.fromPath(url)
            .queryParam("from", fromCurrency)
            .queryParam("to", toCurrency)
            .build();

        HttpResponse response = null;
        try {
            response = HttpRequestHelper.sendRequest(uri.toURL().toString());
        }
        catch(MalformedURLException ex) {
            ex.printStackTrace();
        }
        
        try {
            String resporseString = EntityUtils.toString(response.getEntity());

            JSONParser parser = new JSONParser();
            Object obj = null;
            try {
                obj = parser.parse(resporseString);
            }
            catch(ParseException ex) {
                ex.printStackTrace();
            }
            
            JSONObject resporseJson = (JSONObject)obj;
            
            String fromCurrencyValue = (String)resporseJson.get("from");
            String toCurrencyValue = (String)resporseJson.get("to");
            Float rateValue = Float.valueOf((String)resporseJson.get("rate"));
            
            rateService.addData(fromCurrencyValue, toCurrencyValue, rateValue);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
