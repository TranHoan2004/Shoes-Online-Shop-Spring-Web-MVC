package com.HE180030.service.impl;

import com.HE180030.dto.request.CreateInvoiceRequest;
import com.HE180030.dto.response.InvoiceResponse;
import com.HE180030.model.Account;
import com.HE180030.model.Invoice;
import com.HE180030.repository.AccountRepository;
import com.HE180030.repository.InvoiceRepository;
import com.HE180030.service.InvoiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    InvoiceRepository repo;
    AccountRepository aRepo;
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void insertInvoice(int accountID, CreateInvoiceRequest request, String context) throws Exception {
        logger.info("Insert invoice for account ID: " + accountID);
        Account account = aRepo.findById(accountID).orElseThrow(() -> new Exception("Account not found"));
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Invoice invoice = Invoice.builder()
                .exportDate(LocalDate.parse(request.exportDate(), sdf))
                .totalPrice(request.totalPrice())
                .context(context)
                .typePay(request.typePay())
                .phone(request.phone())
                .delivery(request.delivery())
                .account(account)
                .build();
        repo.save(invoice);
    }

    @Override
    @Transactional
    public void deleteInvoiceByAccountID(int id) {
        logger.info("Delete invoice by account id " + id);
        Account a = aRepo.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        repo.deleteByAccount(a);
    }

    @Override
    public List<InvoiceResponse> getAllInvoicesByID(int id) {
        return List.of();
    }
}
