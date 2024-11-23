package com.HE180030.dto;

import com.HE180030.model.Account;
import com.HE180030.model.Product;

import java.util.Date;

public class ReviewDTO {
    private long id;
    private Product product;
    private Account account;
    private String contentReview;
    private Date dateReview;
}
