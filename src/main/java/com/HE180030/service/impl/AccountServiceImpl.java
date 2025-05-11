package com.HE180030.service.impl;

import com.HE180030.dto.request.CreateAccountRequest;
import com.HE180030.dto.request.UpdateAccountRequest;
import com.HE180030.dto.response.AccountResponse;
import com.HE180030.model.Account;
import com.HE180030.repository.AccountRepository;
import com.HE180030.service.AccountService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    AccountRepository repo;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.repo = accountRepository;
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

    @Override
    public void deleteAccountDTOByID(int id) {

    }

    @Override
    public AccountResponse getAccountDTOByID(int id) {
        return null;
    }
}
