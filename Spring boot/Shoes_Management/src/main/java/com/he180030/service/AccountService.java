package com.HE180030.service;

import com.HE180030.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO login(AccountDTO accountDTO);

    void insertAccountDTO(AccountDTO accountDTO);

    void deleteAccountDTOByID(int id);

    void editAccountDTO(AccountDTO accountDTO);

    void updateProfile(AccountDTO accountDTO);

    void signin(AccountDTO accountDTO);

    int getIdByUsername(String username);

    AccountDTO getAccountDTOByName(String username);

    List<AccountDTO> getAllAccountDTOs();

    AccountDTO getAccountDTOById(int id);

    List<AccountDTO> getListAccountDTOsByPage(List<AccountDTO> list, int start, int end);
}
