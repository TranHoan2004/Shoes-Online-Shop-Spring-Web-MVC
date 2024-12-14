package com.HE180030.repository.impl;

import com.HE180030.model.Account;
import com.HE180030.repository.AccountRepository;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
@DependsOn("sessionFactory")
@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final SessionFactory sessionFactory;

    public AccountRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Account> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Account", Account.class).getResultList();
    }

    @Override
    public Account getById(int id) {
        return sessionFactory.getCurrentSession().get(Account.class, id);
    }

    @Override
    public Account login(String username, String password) {
        return sessionFactory.getCurrentSession().createQuery("from Account a where a.username=:user and a.password=:pass", Account.class)
                .setParameter("user", username)
                .setParameter("pass", password)
                .uniqueResult();
    }

    @Override
    public Account getByName(String username) {
        return sessionFactory.getCurrentSession().createQuery("from Account a where a.username=:username", Account.class)
                .setParameter("username", username)
                .uniqueResult();
    }

    @Override
    public void insert(String user, String pass, boolean isSell, boolean isAdmin, String email) {
        Account account = new Account();
        account.setUsername(user);
        account.setPassword(pass);
        account.setIsSell(isSell);
        account.setIsAdmin(isAdmin);
        account.setEmail(email);
        sessionFactory.getCurrentSession().merge(account);
    }

    @Override
    public List<Account> getListByPage(List<Account> list, int start, int end) {
//        List<Account> arr = new ArrayList<>();
//        for (int i = start; i < end; i++) {
//            arr.add(list.get(i));
//        }
        return sessionFactory.getCurrentSession().createQuery("from Account", Account.class)
                .setFirstResult(start)
                .setMaxResults(end)
                .getResultList();
    }

    @Override
    public void deleteByID(int id) {
        Account account = getById(id);
        sessionFactory.getCurrentSession().remove(account);
    }

    @Override
    public void edit(String username, String password, boolean isSell, boolean isAdmin, String email, int id) {
        Account account = getById(id);
        account.setUsername(username);
        account.setPassword(password);
        account.setIsSell(isSell);
        account.setIsAdmin(isAdmin);
        account.setEmail(email);
        sessionFactory.getCurrentSession().merge(account);
    }

    @Override
    public void updateProfile(String username, String password, String email, int id) {
        Account account = getById(id);
        account.setUsername(username);
        account.setPassword(password);
        account.setEmail(email);
        sessionFactory.getCurrentSession().merge(account);
    }

    @Override
    public void signIn(String username, String pass) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(pass);
        sessionFactory.getCurrentSession().merge(account);
    }

    @Override
    public int getIdByUsername(String username) {
        return sessionFactory.getCurrentSession().createQuery("select a.id from Account a where a.username=:username", Integer.class)
                .setParameter("username", username)
                .uniqueResult();
    }
}
