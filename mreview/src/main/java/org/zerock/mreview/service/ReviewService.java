package org.zerock.mreview.service;

import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getListOfMovie(Long mno);

     Long register(ReviewDTO reviewDTO);

     Long modify(ReviewDTO reviewDTO);

     void delete(Long reviewnum);

    default ReviewDTO entityToDto(Review review) {

        ReviewDTO reviewDTO = ReviewDTO.builder()
                .email(review.getMember().getEmail())
                .grade(review.getGrade())
                .mid(review.getMember().getMid())
                .text(review.getText())
                .reviewnum(review.getReviewnum())
                .nickname(review.getMember().getNickname())
                .mno(review.getMovie().getMno())
                .modDate(review.getModDate())
                .regDate(review.getRegDate())
                .build();

        return reviewDTO;
    }

    default Review dtoToEntity(ReviewDTO reviewDTO) {

        Review review = Review.builder()
                .text(reviewDTO.getText())
                .movie(Movie.builder().mno(reviewDTO.getMno()).build())
                .member(Member.builder().mid(reviewDTO.getMid()).build())
                .reviewnum(reviewDTO.getReviewnum())
                .grade(reviewDTO.getGrade())
                .build();

        return review;
    }
}
