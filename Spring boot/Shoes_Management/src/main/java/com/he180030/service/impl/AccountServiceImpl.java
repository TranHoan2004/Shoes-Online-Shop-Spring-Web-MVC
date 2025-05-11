package com.HE180030.service.impl;

import com.HE180030.dto.AccountDTO;
import com.HE180030.dto.request.CreateAccountRequest;
import com.HE180030.dto.request.UpdateAccountRequest;
import com.HE180030.dto.response.AccountResponse;
import com.HE180030.model.Account;
import com.HE180030.repository.AccountRepository;
import com.HE180030.service.AccountService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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

    @Override
    public void updateProfile(UpdateAccountRequest request) {

    }

    @Override
    public boolean isThisUsernameExisted(String username) {
        return false;
    }

    @Override
    public void insertAccountDTO(CreateAccountRequest accountDTO) {

    }

    @Override
    public Page<AccountResponse> getAllPaginatedAccountDTOs(int page, int size) {
        return null;
    }
}
