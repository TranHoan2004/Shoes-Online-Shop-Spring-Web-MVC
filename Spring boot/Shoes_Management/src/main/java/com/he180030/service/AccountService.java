package com.HE180030.service;

import com.HE180030.dto.request.CreateAccountRequest;
import com.HE180030.dto.request.UpdateAccountRequest;
import com.HE180030.dto.response.AccountResponse;
import org.springframework.data.domain.Page;

public interface AccountService {
    void updateProfile(UpdateAccountRequest request);

    boolean isThisUsernameExisted(String username);

    void insertAccountDTO(CreateAccountRequest accountDTO);

    Page<AccountResponse> getAllPaginatedAccountDTOs(int page, int size);
}
