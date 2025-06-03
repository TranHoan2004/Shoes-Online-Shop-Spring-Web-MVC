package com.HE180030.service.impl;

import com.HE180030.dto.request.CreateInvoiceRequest;
import com.HE180030.dto.response.InvoiceResponse;
import com.HE180030.model.Invoice;
import com.HE180030.repository.InvoiceRepository;
import com.HE180030.service.InvoiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    InvoiceRepository repo;
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void insertInvoice(int accountID, CreateInvoiceRequest request, String context) {

    }

    @Override
    public void deleteInvoiceByAccountID(int id) {
        logger.info("Delete invoice by account id " + id);
        List<Invoice> invoices = repo.findByAccountId(id);
        invoices.forEach(invoice -> {
            invoice.setAccount(null);
            repo.delete(invoice);
        });
    }

    @Override
    public List<InvoiceResponse> getAllInvoicesByID(int id) {
        return List.of();
    }
}
