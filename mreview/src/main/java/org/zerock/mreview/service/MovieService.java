package org.zerock.mreview.service;

import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.MovieImageDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.dto.PageResultDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

    Long register(MovieDTO movieDTO);

    PageResultDTO<MovieDTO,Object[]> getList(PageRequestDTO requestDTO);

    MovieDTO getMovie(Long mno);

    PageResultDTO<MovieDTO, Object[]> getSearch(PageRequestDTO pageRequestDTO, String title);

    // DTO -> Entity
    default Map<String,Object> dtoToEntity(MovieDTO movieDTO){

        // MovieDTO, MovieImageDTO
        Map<String,Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();

        entityMap.put("movie",movie);

        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();

        if(imageDTOList != null && imageDTOList.size() > 0){
            List<MovieImage> movieImageList = imageDTOList.stream().map(movieImageDTO ->{

                MovieImage movieImage = MovieImage.builder()
                        .path(movieImageDTO.getPath())
                        .imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid())
                        .movie(movie)
                        .build();
                return movieImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList", movieImageList);
        }

        return entityMap;
    }


    // Entity -> DTO
    default MovieDTO entitiesToDTO(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt) {
        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();

        if (movieImages != null) { // movieImages가 null이 아닌 경우에만 처리
            List<MovieImageDTO> movieImageDTOList = movieImages.stream()
                    .filter(movieImage -> movieImage != null) // null 체크 추가
                    .map(movieImage -> MovieImageDTO.builder()
                            .imgName(movieImage.getImgName())
                            .path(movieImage.getPath())
                            .uuid(movieImage.getUuid())
                            .build())
                    .collect(Collectors.toList());

            movieDTO.setImageDTOList(movieImageDTOList);
        }

        movieDTO.setAvg(avg != null ? avg.doubleValue() : 0.0);
        movieDTO.setReviewCnt(reviewCnt != null ? reviewCnt.intValue() : 0);

        return movieDTO;
    }

}
