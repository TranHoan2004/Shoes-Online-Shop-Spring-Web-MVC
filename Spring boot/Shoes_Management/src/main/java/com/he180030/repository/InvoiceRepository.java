package com.HE180030.repository;

import com.HE180030.model.Invoice;

import java.util.List;

public interface InvoiceRepository {
    void insert(int accountID, double totalPrice, String context, int phone, String delivery, String name, String typePay);

    void deleteByAccountID(int id);

    List<Invoice> getAllInvoiceByID(int id);
}
