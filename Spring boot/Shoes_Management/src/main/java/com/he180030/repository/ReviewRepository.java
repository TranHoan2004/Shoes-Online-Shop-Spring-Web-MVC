package com.HE180030.repository;

public interface ReviewRepository {
    void deleteByAccountID(int id);

    void deleteByProductID(int productID);
}
