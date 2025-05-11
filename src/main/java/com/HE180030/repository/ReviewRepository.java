package com.HE180030.repository;

import com.HE180030.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
//    void deleteByAccountID(int id);
//
//    void deleteByProductID(int productID);
}
