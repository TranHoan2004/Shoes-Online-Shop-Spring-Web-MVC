package com.HE180030.repository;

import com.HE180030.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("""
            FROM Product p
            WHERE p.cate.id=:categoryId
            """)
    List<Product> findByCategoryId(@Param("categoryId") int categoryId);

    @Query("""
            FROM Product p
            ORDER BY p.id DESC
            LIMIT 1
            """)
    Product findLastProduct();

    @Query("""
            FROM Product p
            WHERE p.name LIKE :text
            ORDER BY p.id DESC
            """)
    List<Product> searchByName(@Param("text") String txt);

    List<Product> findBySellId(int accountID);
}
