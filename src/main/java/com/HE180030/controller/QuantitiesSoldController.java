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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quantity")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class QuantitiesSoldController {
    QuantitiesSoldService qSrv;
    Logger logger = LoggerFactory.getLogger(QuantitiesSoldController.class);

//    public QuantitiesSoldController(QuantitiesSoldService qSrv) {
//        this.qSrv = qSrv;
//    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestBody DeleteRequest request) {
//        logger.info("delete");
//        switch (request.getCode()) {
//            case 100 -> {
        logger.info("Delete Quantities Sold by id: {}", request.id());
        qSrv.deleteQuantitiesSoldDTOByProductID(request.id());
//            }
//            case 101 -> {
//                logger.info("Delete Quantities Sold by id: {}", request.getId());
//            }
//        }
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Quantities sold are successfully deleted")
        );
    }
}
