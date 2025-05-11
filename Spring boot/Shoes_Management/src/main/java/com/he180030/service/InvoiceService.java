package com.HE180030.service;

import com.HE180030.dto.request.CreateInvoiceRequest;
import com.HE180030.dto.response.InvoiceResponse;

import java.util.List;

public interface InvoiceService {
    void insertInvoiceDTO(int accountID, CreateInvoiceRequest request, String context);

    void deleteInvoiceByAccountId(int id);

    List<InvoiceResponse> getAllInvoiceByID(int id);
}