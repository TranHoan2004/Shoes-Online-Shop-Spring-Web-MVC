package com.HE180030.controller;

import com.HE180030.dto.request.CreateInvoiceRequest;
import com.HE180030.dto.response.ApiResponse;
import com.HE180030.dto.response.InvoiceResponse;
import com.HE180030.dto.response.ProductResponse;
import com.HE180030.security.utils.SecurityUtils;
import com.HE180030.service.AccountService;
import com.HE180030.service.CartService;
import com.HE180030.service.InvoiceService;
import com.HE180030.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/invoice")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InvoiceController {
    InvoiceService iSrv;
    CartService cSrv;
    ProductService pSrv;
    AccountService aSrv;
    Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @GetMapping("")
    public ResponseEntity<?> getAllById() {
        logger.info("getAllById");
        int id = aSrv.getIDByEmail(SecurityUtils.getCurrentUser().getUsername());
        List<InvoiceResponse> list = iSrv.getAllInvoicesByID(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(list)
                        .dataSize(list.size())
        );
    }

    @PostMapping("/add_order")
    public ResponseEntity<?> addOrder(
            @RequestBody CreateInvoiceRequest invoiceDTO) {
        logger.info("Add Order");
        StringBuilder context = new StringBuilder();
        int id = aSrv.getIDByEmail(SecurityUtils.getCurrentUser().getUsername());
        AtomicReference<Double> totalMoney = new AtomicReference<>((double) 0);
        cSrv.getCartByAccountID(id).forEach(cartDTO ->
                pSrv.getAllProducts().forEach((ProductResponse productDTO) -> {
                    if (Objects.equals(cartDTO.getProductId(), productDTO.getId())) {
                        totalMoney.updateAndGet(v -> (v + (productDTO.getPrice() * cartDTO.getAmount())));
                        context.append(" Name : ").append(productDTO.getName())
                                .append(": Color : ").append(productDTO.getColor())
                                .append(" Name : ").append(cartDTO.getSize())
                                .append(": Quantity : ").append(cartDTO.getAmount())
                                .append(" // ");
                    }
                }));
        iSrv.insertInvoice(id, invoiceDTO, context.toString());
        double totalMoneyVAT = Math.round(totalMoney.get() * 1.1);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .code(HttpStatus.CREATED.value())
                        .message(HttpStatus.CREATED.getReasonPhrase())
                        .data(Map.of(
                                "total money", totalMoney,
                                "total money VAT", totalMoneyVAT
                        ))
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteInvoice(
            @RequestBody int id) {
        logger.info("Delete invoice with id {}", id);
        iSrv.deleteInvoiceByAccountID(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Delete successfully")
                        .build()
        );
    }
}
