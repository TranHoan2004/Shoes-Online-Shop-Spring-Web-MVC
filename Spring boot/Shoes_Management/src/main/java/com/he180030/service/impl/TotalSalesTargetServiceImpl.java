package com.HE180030.service.impl;

import com.HE180030.repository.TotalSalesTargetRepository;
import com.HE180030.service.TotalSalesTargetService;
import org.springframework.stereotype.Service;

@Service
public class TotalSalesTargetServiceImpl implements TotalSalesTargetService {
    private TotalSalesTargetRepository repository;

    public TotalSalesTargetServiceImpl(TotalSalesTargetRepository repository) {
        this.repository = repository;
    }
}
