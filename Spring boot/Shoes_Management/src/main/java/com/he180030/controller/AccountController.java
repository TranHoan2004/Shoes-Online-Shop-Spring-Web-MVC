package com.HE180030.controller;

import com.HE180030.dto.request.CreateAccountRequest;
import com.HE180030.dto.request.UpdateAccountRequest;
import com.HE180030.dto.response.AccountResponse;
import com.HE180030.dto.response.ApiResponse;
import com.HE180030.service.AccountService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountService aSrv;
    PasswordEncoder encoder;
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    public AccountController(AccountService accountService, PasswordEncoder bCryptPasswordEncoder) {
        this.aSrv = accountService;
        this.encoder = bCryptPasswordEncoder;
    }

    @PatchMapping("/edit_profile")
    public ResponseEntity<?> editProfile(
            @RequestBody UpdateAccountRequest request) {
        logger.info("editProfile");
        aSrv.updateProfile(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Update profile successfully")
        );
    }

    @PostMapping("/add_account")
    public ResponseEntity<?> addAccount(
            @RequestBody CreateAccountRequest account) {
        logger.info("addAccount");
        String rawPassword = account.getPassword();
        String encryptedPassword = encoder.encode(rawPassword);
        String message;
        account.setPassword(encryptedPassword);
        if (!aSrv.isThisUsernameExisted(account.getUsername())) {
            aSrv.insertAccountDTO(account);
            message = "Account added successfully";
        } else {
            message = "User is existing!";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .code(HttpStatus.CREATED.value())
                        .message(message)
        );
    }

    @GetMapping("/manage_account")
    public ResponseEntity<?> manageAccount(
            @RequestParam(value = "page", defaultValue = "1") int page,
            PagedResourcesAssembler<AccountResponse> assembler) {
        logger.info("manageAccount");
        Page<AccountResponse> accounts = aSrv.getAllPaginatedAccountDTOs(page - 1, 6);
        return renderData(assembler, accounts);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(
            @RequestBody int id) {
        logger.info("deleteAccount");
        aSrv.deleteAccountDTOByID(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Account deleted successfully")
        );
    }

    @NotNull
    private ResponseEntity<?> renderData(@NotNull PagedResourcesAssembler<AccountResponse> assembler, Page<AccountResponse> accountDTOs) {
        return ResponseEntity.ok(ApiResponse.builder()
                .data(assembler.toModel(accountDTOs))
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build()
        );
    }
}
