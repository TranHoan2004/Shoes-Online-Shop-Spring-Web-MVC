package com.HE180030.controller;

import com.HE180030.dto.request.DeleteRequest;
import com.HE180030.dto.response.ApiResponse;
import com.HE180030.service.QuantitiesSoldService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quantity")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class QuantitiesSoldController {
    QuantitiesSoldService qSrv;
    Logger logger = LoggerFactory.getLogger(QuantitiesSoldController.class);

    // tested
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestParam("id") String request) {
        logger.info("delete");
        logger.info("Delete Quantities Sold by id: {}", request);
        qSrv.deleteQuantitiesSoldDTOByProductID(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Quantities sold are successfully deleted")
                        .build()
        );
    }
}
