package com.HE180030.service;

import com.HE180030.dto.request.CreateInvoiceRequest;

public interface InvoiceService {
    void insertInvoiceDTO(int accountID, CreateInvoiceRequest request, String context);

    void deleteInvoiceByAccountId(int id);
}
