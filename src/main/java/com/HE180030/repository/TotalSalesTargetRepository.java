package com.HE180030.repository;

import com.HE180030.model.TotalSalesTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TotalSalesTargetRepository extends JpaRepository<TotalSalesTarget, Integer> {
    List<TotalSalesTarget> findByAccountId(int accountId);
}
