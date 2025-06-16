package com.HE180030.controller;

import com.HE180030.dto.request.*;
import com.HE180030.dto.response.*;
import com.HE180030.security.jwt.*;
import com.HE180030.security.utils.SecurityUtils;
import com.HE180030.service.*;
import com.nimbusds.jwt.JWTClaimsSet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Account Controller", description = "Controller for managing accounts")
public class AccountController {
    // <editor-fold> desc="Attributes"
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
    // </editor-fold>

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
     *     "accessToken": "eyJhb",
     *     "refreshToken": "eyJhbG"
     *   },
     *   "dataSize": 0
     * }
     * </pre>
     */
    @Operation(
            summary = "Login using username and password",
            method = "login",
            description = "Used to permit access permission based on username and password",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = TokenResponse.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Invalid token")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsernameAndPasswordRequest request) throws Exception {
        Authentication manager = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
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
     *   "refreshToken": "eyJhbG"
     * }
     * </pre>
     *
     * <p><strong>Response:</strong></p>
     * <pre>
     * {
     *   "code": 200,
     *   "message": "Throw refresh token successfully",
     *   "data": {
     *     "accessToken": "eyJhbG",
     *     "refreshToken": "eyJhb"
     *   },
     *   "dataSize": 0
     * }
     * </pre>
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenRequest refreshToken) throws Exception {
        if (TokenStore.isTokenBlacklisted(refreshToken.refreshToken())) {
            return ResponseEntity.status(401).body("Refresh token is invalidated");
        }

        JWTClaimsSet claims = jwtService.validateToken(refreshToken.refreshToken());
        if (!"refresh".equals(claims.getStringClaim("typ"))) {
            return ResponseEntity.badRequest().body("Invalid token type");
        }

        String username = claims.getSubject();
        String newAccessToken = jwtService.createAccessToken(username);
        String newRefreshToken = jwtService.createRefreshToken(username);

        // Blacklist the old refresh token
        TokenStore.blacklistToken(refreshToken.refreshToken());

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
     *     "refreshToken": "eyJhbGc"
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
        if (TokenStore.isTokenBlacklisted(refreshToken.refreshToken())) {
            return returnResponseData(null,
                    HttpStatus.UNAUTHORIZED.value(),
                    "Refresh token is invalidated"
            );
        }
        JWTClaimsSet claims = jwtService.validateToken(refreshToken.refreshToken());
        if (!"refresh".equals(claims.getStringClaim("typ"))) {
            return returnResponseData(null,
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid token"
            );
        }
        TokenStore.blacklistToken(refreshToken.refreshToken());
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
                ResponseWrapper.builder()
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
                ResponseWrapper.builder()
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
                ResponseWrapper.builder()
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
                ResponseWrapper.builder()
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
        aSrv.updatePassword(request.username(), encoder.encode(request.password()));
        return returnResponseData(
                null,
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase()
        );
    }

    @NotNull
    private ResponseEntity<?> renderData(@NotNull PagedResourcesAssembler<AccountResponse> assembler, Page<AccountResponse> accountDTOs) {
        return ResponseEntity.ok(ResponseWrapper.builder()
                .data(assembler.toModel(accountDTOs))
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build()
        );
    }

    private ResponseEntity<?> returnResponseData(Object result, int code, String message) {
        return ResponseEntity.ok(ResponseWrapper.builder()
                .data(result)
                .code(code)
                .message(message)
                .build()
        );
    }
}
