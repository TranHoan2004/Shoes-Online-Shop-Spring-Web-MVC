package com.HE180030.controller;

import com.HE180030.dto.request.*;
import com.HE180030.dto.response.*;
import com.HE180030.model.Account;
import com.HE180030.security.jwt.*;
import com.HE180030.security.utils.SecurityUtils;
import com.HE180030.service.*;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
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

import java.util.Random;

@RestController
@RequestMapping("/account")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountController {
    AccountService aSrv;
    CartService cSrv;
    ProductService pSrv;
    ReviewService rSrv;
    TotalSalesTargetService tSrv;
    InvoiceService iSrv;
    PasswordEncoder encoder;
    JWTService jwtService;
    AuthenticationManager authenticationManager;
    @NonFinal
    static String code;
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    /**
     * <p>Testing successfully</p>
     * <p><strong>Request Body:</strong></p>
     * <pre>
     * {
     *   "username": "snowhead2003@gmail.com",
     *   "password": "123456"
     * }
     * </pre>
     *
     * <p><strong>Response:</strong></p>
     * <pre>
     * {
     *   "code": 200,
     *   "message": "Throw access token successfully",
     *   "data": {
     *     "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ5b3VyLWFwcCIsInN1YiI6InNub3doZWFkMjAwM0BnbWFpbC5jb20iLCJ0eXAiOiJhY2Nlc3MiLCJleHAiOjE3NTI0NTU2NzMsImlhdCI6MTc0ODg1NTY3M30.WeE6W6I6fKDjgiH4WzbJYezeDiUJf9ERnVsN_1sWyVs",
     *     "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ5b3VyLWFwcCIsInN1YiI6IjEyMzQ1NiIsInR5cCI6InJlZnJlc2giLCJleHAiOjE3NTI0NTU2NzMsImlhdCI6MTc0ODg1NTY3M30.CbwvPy8DJY9BWikePbXKtB3_19WcxsR1MFLeMG62cFc"
     *   },
     *   "dataSize": 0
     * }
     * </pre>
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsernameAndPasswordRequest request) throws Exception {
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

    /**
     * <p>Testing successfully</p>
     * <p><strong>Request Body:</strong></p>
     * <pre>
     * {
     *   "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ5b3VyLWFwcCIsInN1YiI6IjEyMzQ1NiIsInR5cCI6InJlZnJlc2giLCJleHAiOjE3NTI0NTYxNTUsImlhdCI6MTc0ODg1NjE1NX0.Mz1HZiNqOhwtqoTL1BCaJV4btJKOgQKmhOi8oMowhcc"
     * }
     * </pre>
     *
     * <p><strong>Response:</strong></p>
     * <pre>
     * {
     *   "code": 200,
     *   "message": "Throw refresh token successfully",
     *   "data": {
     *     "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ5b3VyLWFwcCIsInN1YiI6IjEyMzQ1NiIsInR5cCI6ImFjY2VzcyIsImV4cCI6MTc1MjQ1NjE2NSwiaWF0IjoxNzQ4ODU2MTY1fQ.sWeVcBws_8lreFgAca0Z57I3cAGChQmPhFQuiC0TliQ",
     *     "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ5b3VyLWFwcCIsInN1YiI6IjEyMzQ1NiIsInR5cCI6InJlZnJlc2giLCJleHAiOjE3NTI0NTYxNjUsImlhdCI6MTc0ODg1NjE2NX0.K-AW9GhE_4TaNH6ykK6ktlrvjot-fEF3kS2gm5UAv4Y"
     *   },
     *   "dataSize": 0
     * }
     * </pre>
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenRequest refreshToken) throws Exception {
        if (TokenStore.isTokenBlacklisted(refreshToken.getRefreshToken())) {
            return ResponseEntity.status(401).body("Refresh token is invalidated");
        }

        JWTClaimsSet claims = jwtService.validateToken(refreshToken.getRefreshToken());
        if (!"refresh".equals(claims.getStringClaim("typ"))) {
            return ResponseEntity.badRequest().body("Invalid token type");
        }

        String username = claims.getSubject();
        String newAccessToken = jwtService.createAccessToken(username);
        String newRefreshToken = jwtService.createRefreshToken(username);

        // Blacklist the old refresh token
        TokenStore.blacklistToken(refreshToken.getRefreshToken());

        return returnResponseData(
                TokenResponse.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build(),
                HttpStatus.OK.value(),
                "Throw refresh token successfully"
        );
    }

    /**
     * <p>Testing successfully</p>
     * <p><strong>Request Body:</strong></p>
     * <pre>
     * {
     *     "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ5b3VyLWFwcCIsInN1YiI6IjEyMzQ1NiIsInR5cCI6InJlZnJlc2giLCJleHAiOjE3NTI0NTcwMDgsImlhdCI6MTc0ODg1NzAwOH0.BAf9kiX6Iqv2PlV_tp44M8M6l6ZZ3GIZ5bh_56ZNmYo"
     * }
     * </pre>
     *
     * <p><strong>Response:</strong></p>
     * <pre>
     * {
     *     "code": 200,
     *     "message": "Logged out successfully",
     *     "dataSize": 0
     * }
     * </pre>
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenRequest refreshToken) throws Exception {
        if (TokenStore.isTokenBlacklisted(refreshToken.getRefreshToken())) {
            return returnResponseData(null,
                    HttpStatus.UNAUTHORIZED.value(),
                    "Refresh token is invalidated"
            );
        }
        JWTClaimsSet claims = jwtService.validateToken(refreshToken.getRefreshToken());
        if (!"refresh".equals(claims.getStringClaim("typ"))) {
            return returnResponseData(null,
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid token"
            );
        }
        TokenStore.blacklistToken(refreshToken.getRefreshToken());
        return returnResponseData(null,
                HttpStatus.OK.value(),
                "Logged out successfully"
        );
    }

    // tested
    @PatchMapping("/edit_profile")
    public ResponseEntity<?> editProfile(@RequestBody UpdateAccountRequest request) {
        logger.info("editProfile");
        aSrv.updateProfile(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Update profile successfully")
                        .build()
        );
    }

    // tested
    @PostMapping("/add")
    public ResponseEntity<?> addAccount(@RequestBody CreateAccountRequest account) {
        logger.info("addAccount");
        String message;
        String encryptedPassword = encoder.encode(account.getPassword());
        account.setPassword(encryptedPassword);
        if (!aSrv.isThisEmailExisted(account.getEmail())) {
            aSrv.insertAccount(account);
            message = "Account added successfully";
        } else {
            message = "User is existing!";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .code(HttpStatus.CREATED.value())
                        .message(message)
                        .build()
        );
    }

    // tested
    @GetMapping("/manage")
    public ResponseEntity<?> manageAccount(
            @RequestParam(value = "page", defaultValue = "1") int page,
            PagedResourcesAssembler<AccountResponse> assembler) {
        logger.info("manageAccount");
        Page<AccountResponse> accounts = aSrv.getAllPaginatedAccounts(page - 1, 6);
        return renderData(assembler, accounts);
    }

    /**
     * <p>Testing successfully</p>
     * <p><strong>Request Body:</strong></p>
     * <pre>
     * header:
     * {
     *     "Authorization": refreshToken
     * }
     * </pre>
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount() {
        logger.info("deleteAccount");
        int id = aSrv.getIDByEmail(SecurityUtils.getCurrentUser().getUsername());
        logger.info("{}", id);

        cSrv.deleteCartByAccountID(id);
        pSrv.deleteProductBySellID(id);
        rSrv.deleteReviewByAccountID(id);
        iSrv.deleteInvoiceByAccountID(id);
        tSrv.deleteTotalSalesTargetByAccountID(id);
        aSrv.deleteAccountByID(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Account deleted successfully")
                        .build()
        );
    }

    /**
     * <p>Testing successfully</p>
     * <p><strong>Request Body:</strong></p>
     * <pre>
     * header:
     * {
     *     "Authorization": refreshToken
     * }
     * </pre>
     */
    @GetMapping("/load")
    public ResponseEntity<?> getAccount() {
        logger.info("load");
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(aSrv.getAccountByEmail(SecurityUtils.getCurrentUser().getUsername()))
                        .build()
        );
    }

    // tested
    @GetMapping("/verify_email")
    public ResponseEntity<?> sendVerifyCode(@RequestParam String email) {
        if (aSrv.isThisEmailExisted(email)) {
            Random random = new Random();
            code = String.valueOf(100000 + random.nextInt(900000));
            return returnResponseData(
                    code,
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase()
            );
        }
        return returnResponseData(
                null,
                HttpStatus.BAD_REQUEST.value(),
                "This email does not exist"
        );
    }

    // tested
    @GetMapping("/code")
    public ResponseEntity<?> verifyCode(@RequestParam String verifyCode) {
        if (code.equals(verifyCode)) {
            return returnResponseData(
                    null,
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase()
            );
        }
        return returnResponseData(
                null,
                HttpStatus.BAD_REQUEST.value(),
                "Invalid code"
        );
    }

    // tested
    @PatchMapping("/reset")
    public ResponseEntity<?> updatePassword(@RequestBody UsernameAndPasswordRequest request) {
        logger.info("updatePassword");
        aSrv.updatePassword(request.getUsername(), encoder.encode(request.getPassword()));
        return returnResponseData(
                null,
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase()
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
