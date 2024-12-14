package com.HE180030.service.impl;

import com.HE180030.dto.InvoiceDTO;
import com.HE180030.repository.InvoiceRepository;
import com.HE180030.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void deleteInvoiceDTOByAccountID(int id) {

    }

    @Override
    public void insertInvoiceDTO(int accountID, double totalPrice, String context, int phone, String delivery, String name, String typePay) {

    }

    @Override
    public List<InvoiceDTO> getAllInvoiceByID(int id) {
        return null;
    }
}
