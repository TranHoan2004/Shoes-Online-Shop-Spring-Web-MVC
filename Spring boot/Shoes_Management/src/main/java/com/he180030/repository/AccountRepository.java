package com.HE180030.repository;

import com.HE180030.model.Account;

import java.util.List;

public interface AccountRepository {

    Account login(String username, String password);

    void insert(String user, String pass, boolean isSell, boolean isAdmin, String email);

    void deleteByID(int id);

    void edit(String username, String password, boolean isSell, boolean isAdmin, String email, int uID);

    void updateProfile(String username, String password, String email, int uID);

    void signIn(String user, String pass);

    int getIdByUsername(String username);

    Account getByName(String username);

    List<Account> getAll();

    Account getById(int id);

    List<Account> getListByPage(List<Account> list, int start, int end);
}
