package com.HE180030.service.impl;

import com.HE180030.model.QuantitiesSold;
import com.HE180030.repository.QuantitiesSoldRepository;
import com.HE180030.service.QuantitiesSoldService;
import com.HE180030.utils.UrlIdEncoder;
import jakarta.persistence.EntityNotFoundException;
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
    public void deleteQuantitiesSoldDTOByProductID(String productID) {
        logger.info("Delete product by id");
        QuantitiesSold qs = repo.findById(UrlIdEncoder.decodeId(productID))
                .orElseThrow(() -> new EntityNotFoundException("QuantitiesSold not found"));
        qs.setProduct(null);
        repo.delete(qs);
    }
}
