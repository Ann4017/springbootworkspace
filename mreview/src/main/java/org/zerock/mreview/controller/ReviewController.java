package org.zerock.mreview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.Review;
import org.zerock.mreview.service.ReviewServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    // 해당 영화의 전체 리뷰 조회
    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno, Model model) {
        log.info("-------------------List");
        log.info("mno: " + mno);

        List<ReviewDTO> reviewDTOList = reviewService.getListOfMovie(mno);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> register(@RequestBody ReviewDTO reviewDTO) {
        log.info(reviewDTO);

        Long rno = reviewService.register(reviewDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modify(@RequestBody ReviewDTO reviewDTO, @PathVariable Long reviewnum) {
        log.info(reviewDTO);
        log.info(reviewnum);

        Long rno = reviewService.modify(reviewDTO);

        return new ResponseEntity<Long>(rno, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public void delete(@PathVariable Long reviewnum) {

        reviewService.delete(reviewnum);
    }



}
