package com.oshurpik.component.currencycrawler;
 
import com.oshurpik.helper.CurrenciesPropertyHepler;
import com.oshurpik.helper.FromCurrToCurr;
import com.oshurpik.service.RateService;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
 
public class CrawlerJob implements Job {
    private final CurrenciesPropertyHepler ph = new CurrenciesPropertyHepler();
    private final String URL = ph.getCurrenciesUrl();
    private final List<FromCurrToCurr> CURRENCIES_PAIRS = ph.getCurrenciesPairs();
    
    public CrawlerJob() {
    }
    
    public void execute(JobExecutionContext context) throws JobExecutionException { 
        ExecutorService executor = Executors.newCachedThreadPool();
        
        JobDataMap jdMap = context.getJobDetail().getJobDataMap();
        RateService rateService = (RateService)jdMap.get("rateService");
        
        for (FromCurrToCurr currency : CURRENCIES_PAIRS) {
            HttpRequestWorker worker = new HttpRequestWorker(URL, currency.getFromCurrency(), currency.getToCurrency(), rateService);
            executor.execute(worker);
        }
        
        executor.shutdown();
        while(!executor.isTerminated()) {}        

    }
 
}
