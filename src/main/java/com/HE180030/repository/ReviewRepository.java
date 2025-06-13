package com.HE180030.repository;

import com.HE180030.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByAccountId(int accountID);

    Review findByProductId(Integer productId);
}
