package com.HE180030.controller;

import com.HE180030.dto.request.IdRequest;
import com.HE180030.dto.response.ResponseWrapper;
import com.HE180030.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/r")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReviewController {
    ReviewService rSrv;
    Logger logger = LoggerFactory.getLogger(ReviewController.class);

    // tested
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReview(
            @RequestBody IdRequest request) {
        logger.info("deleteReview");
        rSrv.deleteReviewByProductID(request.id());
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .code(HttpStatus.OK.value())
                        .message("Review are successfully deleted")
                        .build()
        );
    }
}
