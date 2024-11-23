package com.HE180030.service;

import com.HE180030.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceService {
    void insertInvoiceDTO(long accountID, double totalPrice, String context, int phone, String delivery, String name, String typePay);

    void deleteInvoiceDTOByAccountID(long id);

    List<InvoiceDTO> getAllInvoiceByID(long id);
}
