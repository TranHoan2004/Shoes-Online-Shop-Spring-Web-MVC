package com.HE180030.service.impl;

import com.HE180030.dto.request.CreateAccountRequest;
import com.HE180030.dto.request.UpdateAccountRequest;
import com.HE180030.dto.response.AccountResponse;
import com.HE180030.model.Account;
import com.HE180030.repository.AccountRepository;
import com.HE180030.service.AccountService;
import com.HE180030.utils.UrlIdEncoder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    AccountRepository repo;
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void updateProfile(UpdateAccountRequest request) {
        Account acc = repo.findById(UrlIdEncoder.decodeId(String.valueOf(request.getId())))
                .orElse(null);
        if (acc == null) {
            throw new RuntimeException("Account not found");
        }
        acc.setUsername(request.getUsername());
        acc.setPassword(request.getPassword());
        acc.setIsAdmin(request.isAdmin());
        acc.setIsSell(request.isSell());
        repo.save(acc);
        logger.info("Account updated");
    }

    @Override
    public boolean isThisEmailExisted(String email) {
        logger.info("Checking if email exists");
        return repo.existsAccountByEmail(email);
    }

    @Override
    public void insertAccount(CreateAccountRequest accountDTO) {
        logger.info("Inserting new account");
        repo.save(Account.builder()
                .email(accountDTO.getEmail())
                .password(accountDTO.getPassword())
                .build());
    }

    @Override
    public Page<AccountResponse> getAllPaginatedAccounts(int page, int size) {
        logger.info("Getting all paginated accounts");
        Page<Account> accounts = repo.findAll(PageRequest.of(page, size));
        return accounts.map(a ->
                AccountResponse.builder()
                        .id(UrlIdEncoder.encodeId(a.getId()))
                        .email(a.getEmail())
                        .isAdmin(a.getIsAdmin() != null && a.getIsAdmin())
                        .isSell(a.getIsSell() != null && a.getIsSell())
                        .build()
        );
    }

    @Override
    public void deleteAccountByID(int id) {
        logger.info("Deleting account");
        Optional<Account> account = repo.findById(id);
        account.ifPresent(acc -> {
            acc.getCarts().clear();
            acc.getInvoices().clear();
            acc.getProducts().clear();
            acc.getReviews().clear();
            acc.getTotalSalesTargetList().clear();
            acc.setRole(null);
            repo.delete(acc);
        });
    }

    @Override
    public AccountResponse getAccountByID(int id) {
        return null;
    }

    @Override
    public AccountResponse getAccountByEmail(String email) {
        Account acc = repo.findAccountByEmail(email);
        return AccountResponse.builder()
                .id(UrlIdEncoder.encodeId(acc.getId()))
                .email(acc.getEmail())
                .isAdmin(acc.getIsAdmin() != null && acc.getIsAdmin())
                .isSell(acc.getIsSell() != null && acc.getIsSell())
                .build();
    }

    @Override
    public Integer getIDByEmail(String email) {
        Account account = repo.findAccountByEmail(email);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        return account.getId();
    }

    @Override
    public void updatePassword(String email, String password) {
        logger.info("Updating password");
        Account account = repo.findAccountByEmail(email);
        account.setPassword(password);
        repo.save(account);
    }
}
