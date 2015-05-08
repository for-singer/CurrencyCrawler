package com.oshurpik.repository;

import com.oshurpik.entity.Rate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer>, RateRepositoryCustom {
    
    @Modifying
    @Query("UPDATE Rate r " +
            "SET r.suitable=:suitable " +
            "WHERE r.id IN " +
            "(:idList)")
    public void updateActualRateSuitable(@Param("suitable") boolean suitable, @Param("idList") List<Integer> idList);
    
    @Query("SELECT r " +
            "FROM Rate r " +
            "INNER JOIN r.fromCurrency c " +
            "INNER JOIN r.toCurrency c2 " +
            "WHERE date = " +
            "(SELECT MAX(date) " +
            "    FROM Rate r2 " +
            "    WHERE r2.fromCurrency=r.fromCurrency AND r2.toCurrency=r.toCurrency)")
    public List<Rate> getActualRates();

}
