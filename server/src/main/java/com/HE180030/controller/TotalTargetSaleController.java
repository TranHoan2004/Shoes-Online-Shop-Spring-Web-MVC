package com.HE180030.controller;

import com.HE180030.dto.response.ApiResponse;
import com.HE180030.security.utils.SecurityUtils;
import com.HE180030.service.AccountService;
import com.HE180030.service.TotalSalesTargetService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TotalTargetSaleController {
    Logger logger = LoggerFactory.getLogger(TotalTargetSaleController.class);
    TotalSalesTargetService tSrv;
    AccountService aSrv;

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete() {
        logger.info("Delete sale");
        int id = aSrv.getIDByEmail(SecurityUtils.getCurrentUser().getUsername());
        tSrv.deleteTotalSalesTargetByAccountID(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("delete successfully")
                        .build()
        );
    }
}
