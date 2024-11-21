package com.HE180030.repository.impl;

import com.HE180030.repository.QuantitiesSoldRepository;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
@DependsOn("sessionFactory")
@Repository
public class QuantitiesSoldRepositoryImpl implements QuantitiesSoldRepository {
    @Override
    public void deleteSoLuongDaBanByProductID(long productID) {

    }
}
