package com.HE180030.repository.impl;

import com.HE180030.repository.ReviewRepository;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
@DependsOn("sessionFactory")
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
    private final SessionFactory sessionFactory;

    public ReviewRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void deleteByAccountID(int id) {
        sessionFactory.getCurrentSession()
                .createQuery("delete from Review r where r.account.id = :accountID")
                .setParameter("accountID", id)
                .executeUpdate();
    }

    @Override
    public void deleteByProductID(int productID) {
        sessionFactory.getCurrentSession()
                .createQuery("delete from Review r where r.product.id = :productID")
                .setParameter("productID", productID)
                .executeUpdate();
    }
}
