package com.HE180030.service;

import com.HE180030.dto.request.CreateInvoiceRequest;
import com.HE180030.dto.response.InvoiceResponse;

import java.util.List;

public interface InvoiceService {
    void insertInvoice(int accountID, CreateInvoiceRequest request, String context) throws Exception;

    void deleteInvoiceByAccountID(int id);

    List<InvoiceResponse> getAllInvoicesByID(int id);
}