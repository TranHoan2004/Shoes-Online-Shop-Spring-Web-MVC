package com.HE180030.repository.impl;

import com.HE180030.model.Account;
import com.HE180030.repository.AccountRepository;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@DependsOn("sessionFactory")
@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final SessionFactory sessionFactory;

    public AccountRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Account> getAllAccount() {
        return null;
    }

    @Override
    public Account getAccountById(int id) {
        return null;
    }

    @Override
    public void updateProfile(String username, String password) {

    }

    @Override
    public Account login(String username, String password) {
        return null;
    }

    @Override
    public Account checkAccounExist(String username) {
        return null;
    }

    @Override
    public void insertAccount(String user, String pass, String isSell, String isAdmin, String email) {

    }

    @Override
    public List<Account> getListByPageAccount(List<Account> list, int start, int end) {
        return null;
    }

    @Override
    public void deleteAccount(String id) {

    }

    @Override
    public void editAccount(String username, String password, int isSell, int isAdmin, String email, int uID) {

    }

    @Override
    public void createSignIn(String user, String pass) {

    }
}
