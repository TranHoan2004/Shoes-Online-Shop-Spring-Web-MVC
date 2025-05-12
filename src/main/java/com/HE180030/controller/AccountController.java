package com.HE180030.controller;

import com.HE180030.dto.request.*;
import com.HE180030.dto.response.*;
import com.HE180030.security.jwt.*;
import com.HE180030.security.utils.SecurityUtils;
import com.HE180030.service.AccountService;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.slf4j.*;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/account")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountService aSrv;
    PasswordEncoder encoder;
    JWTService jwtService;
    AuthenticationManager authenticationManager;
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    public AccountController(AccountService accountService, PasswordEncoder bCryptPasswordEncoder, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.aSrv = accountService;
        this.encoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception {
        Authentication manager = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) manager.getPrincipal();
        String accessToken = jwtService.createAccessToken(userDetails.getUsername());
        String refreshToken = jwtService.createRefreshToken(userDetails.getUsername());

        return returnResponseData(
                TokenResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build(),
                HttpStatus.OK.value(),
                "Throw access token successfully"
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String refreshToken) throws Exception {
        if (TokenStore.isTokenBlacklisted(refreshToken)) {
            return ResponseEntity.status(401).body("Refresh token is invalidated");
        }

        JWTClaimsSet claims = jwtService.validateToken(refreshToken);
        if (!"refresh".equals(claims.getStringClaim("typ"))) {
            return ResponseEntity.badRequest().body("Invalid token type");
        }

        String username = claims.getSubject();
        String newAccessToken = jwtService.createAccessToken(username);
        String newRefreshToken = jwtService.createRefreshToken(username);

        // Blacklist the old refresh token
        TokenStore.blacklistToken(refreshToken);

        return returnResponseData(
                TokenResponse.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build(),
                HttpStatus.OK.value(),
                "Throw refresh token successfully"
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String refreshToken) throws Exception {
        if (TokenStore.isTokenBlacklisted(refreshToken)) {
            return returnResponseData(null,
                    HttpStatus.UNAUTHORIZED.value(),
                    "Refresh token is invalidated"
            );
        }
        JWTClaimsSet claims = jwtService.validateToken(refreshToken);
        if (!"refresh".equals(claims.getStringClaim("type"))) {
            return returnResponseData(null,
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid token"
            );
        }
        TokenStore.blacklistToken(refreshToken);
        return returnResponseData(null,
                HttpStatus.OK.value(),
                "Logged out successfully"
        );
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

    @PostMapping("/add")
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

    @GetMapping("/manage")
    public ResponseEntity<?> manageAccount(
            @RequestParam(value = "page", defaultValue = "1") int page,
            PagedResourcesAssembler<AccountResponse> assembler) {
        logger.info("manageAccount");
        Page<AccountResponse> accounts = aSrv.getAllPaginatedAccountDTOs(page - 1, 6);
        return renderData(assembler, accounts);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount() {
        logger.info("deleteAccount");
        aSrv.deleteAccountDTOByID(SecurityUtils.getCurrentUserID());
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Account deleted successfully")
        );
    }

    @GetMapping("/load")
    public ResponseEntity<?> getAccount() {
        logger.info("load");
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(aSrv.getAccountDTOByID(SecurityUtils.getCurrentUserID()))
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

    private ResponseEntity<?> returnResponseData(Object result, int code, String message) {
        return ResponseEntity.ok(ApiResponse.builder()
                .data(result)
                .code(code)
                .message(message)
                .build()
        );
    }
}
