package com.HE180030.service.impl;

import com.HE180030.repository.TotalSalesTargetRepository;
import com.HE180030.service.TotalSalesTargetService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TotalSalesTargetServiceImpl implements TotalSalesTargetService {
    TotalSalesTargetRepository repo;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public TotalSalesTargetServiceImpl(TotalSalesTargetRepository repository) {
        this.repo = repository;
    }

    @Override
    public void deleteTotalSalesTargetByAccountID(int id) {

    }
}
