package com.HE180030.repository.impl;

import com.HE180030.repository.TotalSalesSpendRepository;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
@DependsOn("sessionFactory")
@Repository
public class TotalSalesSpendRepositoryImpl implements TotalSalesSpendRepository {
    @Override
    public void deleteTotalSalesSpendByUserID(long id) {

    }
}
