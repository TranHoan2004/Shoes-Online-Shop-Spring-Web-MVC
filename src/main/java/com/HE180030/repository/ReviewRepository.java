package com.HE180030.repository;

import com.HE180030.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByAccountId(int accountID);

    List<Review> findByProductId(Integer productId);
}
