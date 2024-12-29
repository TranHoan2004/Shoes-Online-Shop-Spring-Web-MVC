package com.HE180030.dto;

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
    private int id;
    private String contentReview;
    private Date dateReview;
}
