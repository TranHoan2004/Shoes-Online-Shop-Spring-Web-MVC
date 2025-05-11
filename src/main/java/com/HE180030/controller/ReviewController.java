package com.HE180030.controller;

import com.HE180030.dto.response.ApiResponse;
import com.HE180030.service.ReviewService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {
    ReviewService rSrv;
    Logger logger = LoggerFactory.getLogger(ReviewController.class);

    public ReviewController(ReviewService rSrv) {
        this.rSrv = rSrv;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReview(
            @RequestBody int id) {
        rSrv.deleteReviewDTOByProductID(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message("Review are successfully deleted")
        );
    }
}
