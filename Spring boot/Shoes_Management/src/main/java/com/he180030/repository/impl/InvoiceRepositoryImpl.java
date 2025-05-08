package com.HE180030.repository.impl;

import com.HE180030.model.Account;
import com.HE180030.model.Invoice;
import com.HE180030.repository.InvoiceRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
//@DependsOn("sessionFactory")
@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {
//    private final SessionFactory sessionFactory;
//
//    public InvoiceRepositoryImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    @Override
    public void deleteByAccountID(int id) {
//        sessionFactory.getCurrentSession().createQuery("delete from Invoice i where i.account.id=:id", Invoice.class)
//                .setParameter("id", id)
//                .executeUpdate();
    }

    @Override
    public void insert(int accountID, double totalPrice,
                       String context, int phone,
                       String delivery, String name,
                       String typePay) {
//        Session session = sessionFactory.getCurrentSession();
//        Account account = session.get(Account.class, accountID);
//        Invoice invoice = Invoice.builder()
//                .account(account)
//                .totalPrice(totalPrice)
//                .context(context)
//                .phone(phone)
//                .delivery(delivery)
//                .name(name)
//                .typepay(typePay)
//                .build();
//        session.merge(invoice);
    }

    @Override
    public List<Invoice> getAllInvoiceByID(int id) {
//        return sessionFactory
//                .getCurrentSession()
//                .createQuery("from Invoice i where i.account.id=:id", Invoice.class)
//                .setParameter("id", id)
//                .getResultList();
        return null;
    }
}
