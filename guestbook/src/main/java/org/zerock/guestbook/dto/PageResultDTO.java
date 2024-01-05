package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {

    private List<DTO> dtoList;

    private int totalPage;

    private int page; // getter, setter

    private int size;

    private int start, end;

    private boolean prev, next; // is

    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        // 페이지 블럭의 마지막 끝 번호 구하기
        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;

        // 시작 페이지 번호 구하기
        start = tempEnd - 9;
        // 이전 페이지
        prev = start > 1;
        // 마지막 페이지
        end = totalPage > tempEnd ? tempEnd : totalPage;
        // 다음 페이지
        next = totalPage > tempEnd;

        // boxed() :IntStream -> Integer -> List<Integer>
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
