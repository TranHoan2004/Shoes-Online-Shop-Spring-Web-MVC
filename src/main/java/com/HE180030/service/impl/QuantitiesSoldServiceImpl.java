package com.HE180030.service.impl;

import com.HE180030.repository.QuantitiesSoldRepository;
import com.HE180030.service.QuantitiesSoldService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuantitiesSoldServiceImpl implements QuantitiesSoldService {
    QuantitiesSoldRepository repo;

    public QuantitiesSoldServiceImpl(QuantitiesSoldRepository repository) {
        repo = repository;
    }

    @Override
    public void deleteQuantitiesSoldDTOByProductID(int productID) {

    }
}
