package com.HE180030.repository;

import com.HE180030.model.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> getAllAccount();

    Account getAccountById(int id);

    void updateProfile(String username, String password);

    Account login(String username, String password);

    Account checkAccounExist(String username);

    void insertAccount(String user, String pass, String isSell, String isAdmin, String email);

    List<Account> getListByPageAccount(List<Account> list, int start, int end);

    void deleteAccount(String id);

    void editAccount(String username, String password, int isSell, int isAdmin, String email, int uID);

    void createSignIn(String user, String pass);
}
