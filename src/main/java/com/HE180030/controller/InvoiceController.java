package com.HE180030.controller;

import com.HE180030.dto.request.CreateInvoiceRequest;
import com.HE180030.dto.request.DeleteInvoiceRequest;
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
import java.util.logging.Level;

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

    /**
     * <p><strong>Response:</strong></p>
     * <pre>
     * {
     *     "code": 200,
     *     "message": "OK",
     *     "data": [],
     *     "dataSize": 0
     * }
     * </pre>
     */
    @GetMapping("/")
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
                        .build()
        );
    }

    /**
     * <p><strong>Request:</strong></p>
     * <pre>
     * {
     *     "totalPrice": "20",
     *     "exportDate": "2025-06-12",
     *     "typePay": "VNPay",
     *     "phone": "0912345678",
     *     "delivery": "Fastcare"
     * }
     * </pre>
     * <p><strong>Response:</strong></p>
     * <pre>
     * {
     *     "code": 201,
     *     "message": "Created",
     *     "data": {
     *         "total money": 828.0,
     *         "total money VAT": 911.0
     *     },
     *     "dataSize": 0
     * }
     * </pre>
     */
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
        try {
            iSrv.insertInvoice(id, invoiceDTO, context.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        double totalMoneyVAT = Math.round(totalMoney.get() * 1.1);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .code(HttpStatus.CREATED.value())
                        .message(HttpStatus.CREATED.getReasonPhrase())
                        .data(Map.of(
                                "total money", totalMoney,
                                "total money VAT", totalMoneyVAT
                        ))
                        .build()
        );
    }

    // Tested
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteInvoice(
            @RequestBody DeleteInvoiceRequest request) {
        logger.info("Delete invoice with id {}", request.id());
        iSrv.deleteInvoiceByAccountID(request.id());
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Delete successfully")
                        .build()
        );
    }
}
