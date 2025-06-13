package com.HE180030.service.impl;

import com.HE180030.model.Review;
import com.HE180030.repository.ReviewRepository;
import com.HE180030.service.ReviewService;
import com.HE180030.utils.UrlIdEncoder;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repo;
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void deleteReviewByProductID(String productID) {
        logger.info("Deleting review by product ID: " + productID);
        Review r = repo.findByProductId(UrlIdEncoder.decodeId(productID));
        if (r == null) {
            throw new EntityNotFoundException("Review with id " + productID + " not found");
        }
        r.setProduct(null);
        r.setAccount(null);
        repo.delete(r);
    }

    @Override
    public void deleteReviewByAccountID(int accountID) {
        logger.info("Delete review by accountID: " + accountID);
        List<Review> reviews = repo.findByAccountId(accountID);
        logger.info("Delete review by accountID: " + reviews);
        reviews.forEach(review -> {
            review.setAccount(null);
            review.setProduct(null);
            repo.delete(review);
        });
    }
}
