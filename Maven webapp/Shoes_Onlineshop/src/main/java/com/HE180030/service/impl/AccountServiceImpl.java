package com.HE180030.service.impl;

import com.HE180030.dto.AccountDTO;
import com.HE180030.model.Account;
import com.HE180030.repository.AccountRepository;
import com.HE180030.service.AccountService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void deleteAccountDTOByID(int id) {

    }

    @Override
    public AccountDTO login(AccountDTO accountDTO) {
        return null;
    }

    @Override
    public void insertAccountDTO(AccountDTO accountDTO) {

    }

    @Override
    public void editAccountDTO(AccountDTO accountDTO) {

    }

    @Override
    public void updateProfile(AccountDTO accountDTO) {
        Account account = convert(accountDTO);
        accountRepository.updateProfile(account.getUsername(), account.getPassword(), account.getEmail(), account.getId());
    }

    @Override
    public void signin(AccountDTO accountDTO) {

    }

    @Override
    public int getIdByUsername(String username) {
        return 0;
    }

    @Override
    public AccountDTO getAccountDTOByName(String username) {
        return null;
    }

    @Override
    public List<AccountDTO> getAllAccountDTOs() {
        List<Account> list = accountRepository.getAll();
        List<AccountDTO> accounts = new ArrayList<>();
        for (Account account : list) {
            accounts.add(convert(account));
        }
        return accounts;
    }

    @Override
    public AccountDTO getAccountDTOById(int id) {
        return null;
    }

    @Override
    public List<AccountDTO> getListAccountDTOsByPage(List<AccountDTO> list, int start, int end) {
        return null;
    }

    private AccountDTO convert(@NotNull Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .email(account.getEmail())
                .isAdmin(account.getIsAdmin())
                .isSell(account.getIsSell())
                .password(account.getPassword())
                .username(account.getUsername())
                .build();
    }

    private Account convert(@NotNull AccountDTO account) {
        return Account.builder()
                .id(account.getId())
                .email(account.getEmail())
                .isAdmin(account.getIsAdmin())
                .isSell(account.getIsSell())
                .password(account.getPassword())
                .username(account.getUsername())
                .build();
    }
}
