package org.zerock.mreview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;
import org.zerock.mreview.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDTO> getListOfMovie(Long mno) {

       List<Review> result = reviewRepository.findByMovie(Movie.builder()
               .mno(mno)
               .build());

        List<ReviewDTO> reviewDTOList = new ArrayList<>();

        for (Review review : result) {
            ReviewDTO reviewDTO = entityToDto(review);
            reviewDTOList.add(reviewDTO);
        }

        /*Movie movie = Movie.builder().mno(mno).build();
        List<Review> result2 = reviewRepository.findByMovie(movie);

        List<ReviewDTO> list = result2.stream().map(movieReview -> entityToDto(movieReview)).collect(Collectors.toList());*/

        return reviewDTOList;
    }

    @Override
    public Long register(ReviewDTO reviewDTO) {

        Review review = dtoToEntity(reviewDTO);

        reviewRepository.save(review);

        return review.getReviewnum();
    }

    @Override
    public Long modify(ReviewDTO reviewDTO) {

        Review review = dtoToEntity(reviewDTO);

        reviewRepository.save(review);

        return review.getReviewnum();
    }

    @Override
    public void delete(Long reviewnum) {

        Optional<Review> result = reviewRepository.findById(reviewnum);

        if (result.isPresent()) {
            reviewRepository.delete(result.get());
        }
    }
}
