package com.HE180030.service.impl;

import com.HE180030.dto.AccountDTO;
import com.HE180030.repository.AccountRepository;
import com.HE180030.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void deleteAccountDTOByID(long id) {

    }

    @Override
    public AccountDTO login(String username, String password) {
        return null;
    }

    @Override
    public void insertAccountDTO(String user, String pass, boolean isSell, boolean isAdmin, String email) {

    }

    @Override
    public void editAccountDTO(String username, String password, boolean isSell, boolean isAdmin, String email, long uID) {

    }

    @Override
    public void updateProfile(String username, String password, String email, long uID) {

    }

    @Override
    public void signIn(String user, String pass) {

    }

    @Override
    public int getIdByUsername(String username) {
        return 0;
    }

    @Override
    public AccountDTO getByName(String username) {
        return null;
    }

    @Override
    public List<AccountDTO> getAllAccountDTOs() {
        return null;
    }

    @Override
    public AccountDTO getAccountDTOById(long id) {
        return null;
    }

    @Override
    public List<AccountDTO> getListAccountDTOsByPage(List<AccountDTO> list, int start, int end) {
        return null;
    }
}
