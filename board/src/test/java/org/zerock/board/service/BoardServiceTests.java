package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {

        BoardDTO dto = BoardDTO.builder()
                .title("test...")
                .content("test...")
                .writerEmail("user55@aaa.com")
                .build();

        Long bno = boardService.register(dto);
    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> resultDTO = boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO : resultDTO.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet() {

        BoardDTO result = boardService.get(100L);

        System.out.println(result);
    }

    @Test
    public void testDelete() {

        boardService.removeWithReplies(1L);
    }

    @Test
    public void testModify() {
        BoardDTO result = BoardDTO.builder()
                .bno(2L)
                .title("title")
                .content("content")
                .build();

        boardService.modify(result);
    }
}
