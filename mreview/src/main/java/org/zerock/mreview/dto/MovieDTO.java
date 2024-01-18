package org.zerock.mreview.dto;

import lombok.*;
import org.zerock.mreview.entity.MovieImage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long mno;

    private String title;

    @Builder.Default
    private List<MovieImageDTO> imageDTOList = new ArrayList<>();

    private double avg;

    private int reviewCnt;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

}
