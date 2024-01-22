package org.zerock.mreview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.MovieImageDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.dto.PageResultDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;
import org.zerock.mreview.repository.MovieImageRepository;
import org.zerock.mreview.repository.MovieRepository;

import java.util.*;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieImageRepository imageRepository;

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String,Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie)entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>)entityMap.get("imgList");

        movieRepository.save(movie);

        movieImageList.forEach(movieImage -> {
            imageRepository.save(movieImage);
        });

        return movie.getMno();
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getListPage(pageable);

        log.info("==============================================");

        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
        });

        log.info("==============================================");

//        List<Object[]> movie =  result.getContent();
//        Object[] m = movie.get(0);
//        System.out.println((Movie)m[0]);
//        System.out.println((List<MovieImage>)(Arrays.asList((MovieImage)m[1])));
//        System.out.println( (Double) m[2]);
//        System.out.println( (Long)m[3]);

        Function<Object[], MovieDTO> fn = (arr -> entitiesToDTO(
                (Movie)arr[0] ,
                (List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
                (Double) arr[2],
                (Long)arr[3])
        );

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public MovieDTO getMovie(Long mno) {

        List<Object[]> result = movieRepository.getMovieWithAll(mno);

        Object[] data = result.get(0);

        Movie movie = (Movie) data[0];
        Double avg = (Double) data[2];
        Long reviewCnt = (Long) data[3];

        List<MovieImage> movieImageList = new ArrayList<>();
        result.forEach(arr ->{
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);
        });


        return entitiesToDTO(movie, movieImageList, avg, reviewCnt);
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getSearch(PageRequestDTO pageRequestDTO, String title) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getSearchPage(pageable, title);

        log.info("==============================================");

        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
        });

        log.info("==============================================");

        Function<Object[], MovieDTO> fn = (arr -> entitiesToDTO(
                (Movie)arr[0] ,
                (List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
                (Double) arr[2],
                (Long)arr[3])
        );

        return new PageResultDTO<>(result,fn);
    }
}