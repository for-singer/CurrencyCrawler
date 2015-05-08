package com.oshurpik.component.currencyhandler;
 
import com.oshurpik.entity.Rate;
import com.oshurpik.helper.RouteHelper;
import com.oshurpik.helper.CurrenciesPropertyHepler;
import com.oshurpik.service.RateService;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
 
public class HandlerJob implements Job {
    private final CurrenciesPropertyHepler ph = new CurrenciesPropertyHepler();
    private final Integer CURRENCY_HANDLER_MAX_LENGTH = ph.getCurrenciesHandlerMaxLength();
    
    public HandlerJob() {
    }
    
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ExecutorService executor = Executors.newCachedThreadPool();
        
        JobDataMap jdMap = context.getJobDetail().getJobDataMap();
        RateService rateService = (RateService)jdMap.get("rateService");
        
        List<Rate> rates = rateService.getActualRates();
        List<Integer> currenciesId = rateService.getActualRatesIds();
        
        boolean result = false; 
        for (int i = 0; i < currenciesId.size(); i++) {
            
            final RouteHelper permutator = new RouteHelper(rates, currenciesId.get(i), CURRENCY_HANDLER_MAX_LENGTH);

            try {
                result = (boolean)executor.submit(permutator).get();
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }

            if (!result) {                
                break;
            }
        }
        
        if (result) {
            System.err.println("Good loop123456");
            rateService.updateActualRateSuitable(true);
        }
        else {
            System.err.println("Bad loop123456");
            rateService.updateActualRateSuitable(false);
        }

        executor.shutdown();
        while(!executor.isTerminated()) {}  

    }
 
}
