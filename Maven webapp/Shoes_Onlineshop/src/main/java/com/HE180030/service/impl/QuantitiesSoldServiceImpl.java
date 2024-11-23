package com.HE180030.service.impl;

import com.HE180030.repository.QuantitiesSoldRepository;
import com.HE180030.service.QuantitiesSoldService;
import org.springframework.stereotype.Service;

@Service
public class QuantitiesSoldServiceImpl implements QuantitiesSoldService {
    private final QuantitiesSoldRepository quantitiesSoldRepository;

    public QuantitiesSoldServiceImpl(QuantitiesSoldRepository repository) {
        quantitiesSoldRepository = repository;
    }

    @Override
    public void deleteQuantitiesSoldDTOByProductID(long productID) {

    }
}
