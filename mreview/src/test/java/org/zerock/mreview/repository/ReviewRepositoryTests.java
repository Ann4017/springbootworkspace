package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertReviews() {
        IntStream.rangeClosed(1, 200).forEach(i -> {
            Long mno = (long)(Math.random() * 100) + 1;
            Long mid = (long)(Math.random() * 100) + 1;
            Movie movie = Movie.builder().mno(mno).build();
            Member member = Member.builder().mid(mid).build();

            Review review = Review.builder()
                    .member(member)
                    .movie(movie)
                    .grade((int)(Math.random() * 5) + 1)
                    .text("리뷰 내용 " + i)
                    .build();

            reviewRepository.save(review);
        });
    }

    @Test
    //@Transactional
    public void testGetMovieReviews() {

        Movie movie = Movie.builder()
                .mno(98L)
                .build();

        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(movieReview -> {
            System.out.println(movieReview.getReviewnum());
            System.out.println(movieReview.getGrade());
            System.out.println(movieReview.getText());
            System.out.println(movieReview.getMember().getEmail());
            System.out.println("---------------------------------");
        });
    }
}
