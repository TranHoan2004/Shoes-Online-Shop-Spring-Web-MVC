package com.HE180030.service.impl;

import com.HE180030.repository.QuantitiesSoldRepository;
import com.HE180030.service.QuantitiesSoldService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class QuantitiesSoldServiceImpl implements QuantitiesSoldService {
    QuantitiesSoldRepository repo;
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void deleteQuantitiesSoldDTOByProductID(int productID) {

    }
}
