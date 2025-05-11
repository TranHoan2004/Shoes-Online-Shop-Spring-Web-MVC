package com.HE180030.service.impl;

import com.HE180030.dto.request.CreateInvoiceRequest;
import com.HE180030.dto.response.InvoiceResponse;
import com.HE180030.repository.InvoiceRepository;
import com.HE180030.service.InvoiceService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceServiceImpl implements InvoiceService {
    InvoiceRepository repo;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.repo = invoiceRepository;
    }

    @Override
    public void insertInvoiceDTO(int accountID, CreateInvoiceRequest request, String context) {

    }

    @Override
    public void deleteInvoiceByAccountId(int id) {

    }

    @Override
    public List<InvoiceResponse> getAllInvoiceByID(int id) {
        return List.of();
    }
}
