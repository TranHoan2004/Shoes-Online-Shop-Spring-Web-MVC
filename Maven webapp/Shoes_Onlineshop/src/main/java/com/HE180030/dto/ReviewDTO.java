package com.HE180030.dto;

import com.HE180030.model.Account;
import com.HE180030.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private long id;
    private Product product;
    private Account account;
    private String contentReview;
    private Date dateReview;
}
