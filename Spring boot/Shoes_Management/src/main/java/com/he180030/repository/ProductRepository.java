package com.HE180030.repository;

import com.HE180030.model.Product;

import java.util.List;

public interface ProductRepository {
    Product getLastProduct();

    List<Product> getAll();

    List<Product> getAllByCategoryID(int categoryId);

    List<Product> searchByName(String txt);

    Product getByID(int id);

    List<Product> getListByPage(List<Product> list, int start, int end);

    List<Product> getAllByAccountID(int sellID);

    void deleteByAccountID(int id);

    void deleteByID(int id);

    void update(String pname, String pimage, double pprice, String ptitle, String pdescription, int pcategory, String pmodel, String pcolor, String pdelivery, String pimage2, String pimage3, String pimage4, int pid) throws Exception;

    void insert(String name, String image, double price, String title, String description, int category, int sid, String model, String color, String delivery, String image2, String image3, String image4);
}
