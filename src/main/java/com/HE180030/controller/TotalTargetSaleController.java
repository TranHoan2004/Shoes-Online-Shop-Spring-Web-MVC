package com.HE180030.controller;

import com.HE180030.dto.response.ApiResponse;
import com.HE180030.service.TotalSalesTargetService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TotalTargetSaleController {
    Logger logger = LoggerFactory.getLogger(TotalTargetSaleController.class);
    TotalSalesTargetService tSrv;

    public TotalTargetSaleController(TotalSalesTargetService tSrv) {
        this.tSrv = tSrv;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestBody int id) {
        logger.info("Delete sale");
        tSrv.deleteTotalSalesTargetByAccountID(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("delete successfully")
        );
    }
}
