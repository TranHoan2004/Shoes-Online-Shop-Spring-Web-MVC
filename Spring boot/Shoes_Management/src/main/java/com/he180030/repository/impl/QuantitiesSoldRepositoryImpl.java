package com.HE180030.repository.impl;

import com.HE180030.repository.QuantitiesSoldRepository;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
@DependsOn("sessionFactory")
@Repository
public class QuantitiesSoldRepositoryImpl implements QuantitiesSoldRepository {
    private final SessionFactory sessionFactory;

    public QuantitiesSoldRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void deleteByProductID(int productID) {
        sessionFactory.getCurrentSession()
                .createQuery("delete from QuantitesSold q where q.product.id = :productID")
                .setParameter("productID", productID)
                .executeUpdate();
    }
}
