package com.HE180030.service.impl;

import com.HE180030.repository.ReviewRepository;
import com.HE180030.service.ReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void deleteReviewDTOByProductID(int productID) {

    }
}
