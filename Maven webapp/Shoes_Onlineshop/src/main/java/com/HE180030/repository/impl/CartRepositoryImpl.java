package com.HE180030.repository.impl;

import com.HE180030.model.Account;
import com.HE180030.model.Cart;
import com.HE180030.model.Product;
import com.HE180030.repository.CartRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
@DependsOn("sessionFactory")
@Repository
public class CartRepositoryImpl implements CartRepository {
    private final SessionFactory sessionFactory;

    public CartRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createAmountAndSize(long accountID, long productID, int amount, String size) {
        Session session = sessionFactory.getCurrentSession();
        Cart cart = session.createQuery(
                "from Cart c " +
                "where c.account.id=:accountID and c.product.id=:productID", Cart.class)
                .setParameter("accountID", accountID)
                .setParameter("productID", productID)
                .uniqueResult();
        cart.setAmount(amount);
        cart.setSize(size);
        session.merge(cart);
    }

    @Override
    public List<Cart> getByAccountID(long accountID) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Cart c where c.account.id=:accountID", Cart.class)
                .setParameter("accountID", accountID)
                .getResultList();
    }

    @Override
    public void updateAmountCart(long accountID, long productID, int amount) {
        Cart cart = sessionFactory
                .getCurrentSession()
                .createQuery("from Cart c where c.account.id=:accountID and c.product.id=:productID", Cart.class)
                .setParameter("accountID", accountID)
                .setParameter("productID", productID)
                .getSingleResult();
        cart.setAmount(amount);
        sessionFactory.getCurrentSession().merge(cart);
    }

    @Override
    public void deleteByProductID(long productID) {
        sessionFactory.getCurrentSession().createQuery("delete from Cart c where c.product.id=:productID", Cart.class)
                .setParameter("productID", productID).executeUpdate();
    }

    @Override
    public void deleteByAccountID(long id) {
        sessionFactory.getCurrentSession().createQuery("delete from Cart c where c.account.id=:id", Cart.class)
                .setParameter("id", id).executeUpdate();
    }

    @Override
    public void create(long accountID, long productID, int amount, String size) {
        Session session = sessionFactory.getCurrentSession();
        Account account = session.createQuery("from Account a where a.id=:accountID", Account.class).setParameter("accountID", accountID).uniqueResult();
        Product product = session.createQuery("from Product a where a.id=:productID", Product.class).setParameter("productID", productID).uniqueResult();
        Cart cart = Cart.builder()
                .account(account)
                .product(product)
                .amount(amount)
                .size(size)
                .build();
        session.merge(cart);
    }

    @Override
    public Cart getByAccountIDAndProductID(long accountID, long productID, String size) {
        return sessionFactory.getCurrentSession().createQuery("select c from Cart c where c.account.id=:accountID and c.product.id=:productID and c.size=:size", Cart.class)
                .setParameter("accountID", accountID)
                .setParameter("productID", productID)
                .setParameter("size", size)
                .uniqueResult();
    }

    @Override
    public void editAmountAndSize(long accountID, long productID, int amount, String size) {
        Session session = sessionFactory.getCurrentSession();
        Cart cart = session.createQuery("from Cart c where c.account.id=:accountID and c.product.id=:productID", Cart.class).uniqueResult();
        cart.setAmount(amount);
        cart.setSize(size);
        session.merge(cart);
    }

    @Override
    public void editAmount(long accountID, long productID, int amount) {
        Session session = sessionFactory.getCurrentSession();
        Cart cart = session.createQuery("from Cart c where c.account.id=:accountID and c.product.id=:productID", Cart.class).uniqueResult();
        cart.setAmount(amount);
        session.merge(cart);
    }
}
