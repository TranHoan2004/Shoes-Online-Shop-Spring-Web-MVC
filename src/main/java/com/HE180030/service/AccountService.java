package com.HE180030.service;

import com.HE180030.dto.request.CreateAccountRequest;
import com.HE180030.dto.request.UpdateAccountRequest;
import com.HE180030.dto.response.AccountResponse;
import org.springframework.data.domain.Page;

public interface AccountService {
    void updateProfile(UpdateAccountRequest request);

    boolean isThisEmailExisted(String email);

    void insertAccount(CreateAccountRequest accountDTO);

    Page<AccountResponse> getAllPaginatedAccounts(int page, int size);

    void deleteAccountByID(int id);

    AccountResponse getAccountByID(int id);

    AccountResponse getAccountByEmail(String email);

    Integer getIDByEmail(String email);
}
