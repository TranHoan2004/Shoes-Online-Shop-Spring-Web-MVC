package com.HE180030.repository;

import com.HE180030.model.Account;

import java.util.List;

public interface AccountRepository {

//    void updateProfile(String username, String password);

    Account login(String username, String password);

    void insertAccount(String user, String pass, int isSell, int isAdmin, String email);

    void deleteAccountByID(long id);

    void editAccount(String username, String password, int isSell, int isAdmin, String email, long uID);

    void updateProfile(String username, String password, String email, long uID);

    void signIn(String user, String pass);

    int getIdByUsername(String username);

    Account getByName(String username);

    List<Account> getAllAccount();

    Account getAccountById(long id);

    List<Account> getListByPageAccount(List<Account> list, int start, int end);

    List<Account> getListAccountsByPage(List<Account> list, int start, int end);

}
