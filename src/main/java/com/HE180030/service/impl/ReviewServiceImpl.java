package com.HE180030.service.impl;

import com.HE180030.repository.ReviewRepository;
import com.HE180030.service.ReviewService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repo;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.repo = reviewRepository;
    }

    @Override
    public void deleteReviewDTOByProductID(int productID) {

    }
}
