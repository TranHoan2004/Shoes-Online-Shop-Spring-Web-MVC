package com.HE180030.controller;

import com.HE180030.dto.AccountDTO;
import com.HE180030.dto.request.CreateInvoiceRequest;
import com.HE180030.dto.response.ApiResponse;
import com.HE180030.dto.response.ProductResponse;
import com.HE180030.service.CartService;
import com.HE180030.service.InvoiceService;
import com.HE180030.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/invoice")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceController {
    InvoiceService iSrv;
    CartService cSrv;
    ProductService pSrv;
    HttpSession session;
    Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    public InvoiceController(InvoiceService invoiceService, CartService cSrv, ProductService pSrv, HttpSession session) {
        this.iSrv = invoiceService;
        this.cSrv = cSrv;
        this.pSrv = pSrv;
        this.session = session;
    }

    @PostMapping("/add_order")
    public ResponseEntity<?> addOrder(
            @RequestBody CreateInvoiceRequest invoiceDTO) {
        logger.info("Add Order");
        StringBuilder context = new StringBuilder();
        AtomicReference<Double> totalMoney = new AtomicReference<>((double) 0);
        cSrv.getCartDTOByAccountID(getAccountID()).forEach(cartDTO ->
                pSrv.getAllProducts().forEach((ProductResponse productDTO) -> {
                    if (cartDTO.getProductId() == productDTO.getId()) {
                        totalMoney.updateAndGet(v -> (v + (productDTO.getPrice() * cartDTO.getAmount())));
                        context.append(" Name : ").append(productDTO.getName())
                                .append(": Color : ").append(productDTO.getColor())
                                .append(" Name : ").append(cartDTO.getSize())
                                .append(": Quantity : ").append(cartDTO.getAmount())
                                .append(" // ");
                    }
                }));
        iSrv.insertInvoiceDTO(getAccountID(), invoiceDTO, context.toString());
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
        iSrv.deleteInvoiceByAccountId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Delete successfully")
                        .build()
        );
    }

    private int getAccountID() {
        AccountDTO account = (AccountDTO) session.getAttribute("account");
        return account.getId();
    }
}
