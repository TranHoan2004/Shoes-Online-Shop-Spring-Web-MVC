package com.HE180030.repository.impl;

import com.HE180030.repository.ReviewRepository;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
@DependsOn("sessionFactory")
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
    @Override
    public void deleteReviewByAccountID(long id) {

    }

    @Override
    public void deleteReviewByProductID(long productID) {

    }
}
