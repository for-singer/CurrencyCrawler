package com.oshurpik.helper;

import com.oshurpik.entity.Rate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class RouteHelper implements Callable<Object> {
    private final Integer startRateId;
    private final Integer maxLength;
    private final List<Rate> rates;
    
    private DirectedGraph<Integer, DefaultEdge> graph;
    private final List<Integer> currentRoute = new ArrayList<>();
    private final List<List<Integer>> cycleList = new ArrayList<>();
    
    public RouteHelper(List<Rate> rates, Integer startRateId, Integer maxLength) {
        this.rates = rates;
        this.startRateId = startRateId;
        this.maxLength = maxLength;   
        
        createGraph();        
    }
    
    private void createGraph() {
        graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        for (Rate r : rates) {
            graph.addVertex(r.getFromCurrency().getId());
            graph.addVertex(r.getToCurrency().getId());
            graph.addEdge(r.getFromCurrency().getId(), r.getToCurrency().getId());
        }      
    }

    private void go(int curr) {
        currentRoute.add(curr);
        for (int i = 0; i < graph.vertexSet().size(); i++) {
            if (currentRoute.size() > 1 && currentRoute.get(0).equals(currentRoute.get(currentRoute.size() - 1))) {
                List<Integer> sequence = new ArrayList<>();
                for (int item : currentRoute) {
                    sequence.add(item);
                }
                cycleList.add(sequence);
                break;
            }
            
            if ( (!currentRoute.contains(i) || (i == currentRoute.get(0))) && graph.containsEdge(curr, i) && currentRoute.size() <= maxLength) {
                if (currentRoute.get(currentRoute.size() - 1) == i) {
                    currentRoute.add(i);
                }                
                go(i);                
            }
        }
        
        currentRoute.remove(currentRoute.size() - 1);
    }
    
    private float getRate(int fromId, int toId) {
        float rateValue = 0.0f;
            
        for (Rate rate : rates) {
            if ((int)rate.getFromCurrency().getId() == fromId && (int)rate.getToCurrency().getId() == toId) {
                rateValue = rate.getRate();
                break;
            }
        }
        return rateValue;
    }
    
    private boolean makeConvertions() {
        final double START_AMOUNT = 1000;
        
        for (List<Integer> sequenceList : cycleList) {
            double res = START_AMOUNT;
            
            for (int j = 0; j < sequenceList.size() - 1; j++) {
                res *= getRate(sequenceList.get(j), sequenceList.get(j + 1));
            }            
            
            //  result checking as it is
            if (res == 0 || res > START_AMOUNT) {
            
            //  result checking to get suitable true(only 2 numbers after coma)
//            if (res == 0 || Math.floor(res * 100) / 100 > START_AMOUNT) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public Object call() {
        go(startRateId);
        return makeConvertions();
    }
}
