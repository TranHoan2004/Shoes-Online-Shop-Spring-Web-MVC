package com.HE180030.service;

import com.HE180030.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceService {
    void insertInvoiceDTO(int accountID, InvoiceDTO invoiceDTO);

    void deleteInvoiceDTOByAccountID(int id);

    List<InvoiceDTO> getAllInvoiceByID(int id);
}
