package com.oshurpik.config;

import com.oshurpik.component.currencycrawler.CrawlerJob;
import com.oshurpik.component.currencyhandler.HandlerJob;
import com.oshurpik.helper.CurrenciesPropertyHepler;
import com.oshurpik.service.RateService;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.oshurpik")
@EnableAutoConfiguration
@EnableWebMvc
@Import({PersistenceContext.class})
@PropertySource("classpath:application.properties")
public class WebAppConfig {
    
    @Autowired
    private RateService rateService;
    
    @Autowired
    private Scheduler scheduler;
    
    @Autowired
    CurrenciesPropertyHepler currenciesPropertyHepler;
    
    @Bean
    public Scheduler taskScheduler() throws SchedulerException {
        return new StdSchedulerFactory().getScheduler();
    }
    
    @PostConstruct
    public void init() {
        startCrawlerJob();
        startHaldlerJob();
    }
    
    public void startCrawlerJob() {  
        Integer crawlerPeriodicity = currenciesPropertyHepler.getCurrenciesCrawlerPeriodicity();
        
        JobDetail job = new JobDetail();
        JobDataMap jobDataMap=new JobDataMap();
        jobDataMap.put("rateService", rateService);
        job.setJobDataMap(jobDataMap);
                
        job.setName("crawlerJobName");
    	job.setJobClass(CrawlerJob.class);
        
    	SimpleTrigger trigger = new SimpleTrigger();
    	trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
        trigger.setName("crawlerTrigger");
    	trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
    	trigger.setRepeatInterval(crawlerPeriodicity);
 
    	try {
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        }
        catch(SchedulerException ex) {
            ex.printStackTrace();
        }
    }
    
    public void startHaldlerJob() {  
        Integer crawlerPeriodicity = currenciesPropertyHepler.getCurrenciesCrawlerPeriodicity();
 
        JobDetail job = new JobDetail();
        JobDataMap jobDataMap=new JobDataMap();
        jobDataMap.put("rateService", rateService);
        job.setJobDataMap(jobDataMap);
        
        job.setName("handlerJobName");
    	job.setJobClass(HandlerJob.class);
        
    	SimpleTrigger trigger = new SimpleTrigger();
    	trigger.setStartTime(new Date(System.currentTimeMillis() + 2000));
        trigger.setName("handlerTrigger");
    	trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
    	trigger.setRepeatInterval(crawlerPeriodicity);
 
    	try {
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        }
        catch(SchedulerException ex) {
            ex.printStackTrace();
        }
    }
    
}
