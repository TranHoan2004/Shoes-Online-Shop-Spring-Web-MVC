package com.HE180030.service;

import com.HE180030.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceService {
    void insertInvoiceDTO(int accountID, double totalPrice, String context, int phone, String delivery, String name, String typePay);

    void deleteInvoiceDTOByAccountID(int id);

    List<InvoiceDTO> getAllInvoiceByID(int id);
}
