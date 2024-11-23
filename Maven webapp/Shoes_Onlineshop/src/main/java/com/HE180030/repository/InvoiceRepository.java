package com.HE180030.repository;

import com.HE180030.model.Invoice;

import java.util.List;

public interface InvoiceRepository {
    void insert(long accountID, double totalPrice, String context, int phone, String delivery, String name, String typePay);

    void deleteByAccountID(long id);

    List<Invoice> getAllInvoiceByID(long id);
}
