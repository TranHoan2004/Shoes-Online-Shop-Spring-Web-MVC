package com.HE180030.repository;

import com.HE180030.model.Account;

import java.util.List;

public interface AccountRepository {

    Account login(String username, String password);

    void insert(String user, String pass, boolean isSell, boolean isAdmin, String email);

    void deleteByID(long id);

    void edit(String username, String password, boolean isSell, boolean isAdmin, String email, long uID);

    void updateProfile(String username, String password, String email, long uID);

    void signIn(String user, String pass);

    int getIdByUsername(String username);

    Account getByName(String username);

    List<Account> getAll();

    Account getById(long id);

    List<Account> getListByPage(List<Account> list, int start, int end);
}
