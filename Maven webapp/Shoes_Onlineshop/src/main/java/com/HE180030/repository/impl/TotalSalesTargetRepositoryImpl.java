package com.HE180030.repository.impl;

import com.HE180030.repository.TotalSalesTargetRepository;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
@DependsOn("sessionFactory")
@Repository
public class TotalSalesTargetRepositoryImpl implements TotalSalesTargetRepository {
    private final SessionFactory sessionFactory;

    public TotalSalesTargetRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void deleteByAccountID(long id) {
        sessionFactory.getCurrentSession()
                .createQuery("delete TotalSalesTarget t where t.account.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
