package com.HE180030.repository;

public interface ReviewRepository {
    void deleteReviewByAccountID(long id);

    void deleteReviewByProductID(long productID);
}
