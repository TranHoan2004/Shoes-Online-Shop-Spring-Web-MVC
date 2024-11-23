package com.HE180030.repository;

public interface ReviewRepository {
    void deleteByAccountID(long id);

    void deleteByProductID(long productID);
}
