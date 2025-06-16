package com.HE180030.repository;

import com.HE180030.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("""
            FROM Cart c
            WHERE c.account.id=:accountID
            """)
    List<Cart> getByAccountId(@Param("accountID") int accountID);

    Cart findByAccountIdAndProductId(int accountID, int productID);
}
