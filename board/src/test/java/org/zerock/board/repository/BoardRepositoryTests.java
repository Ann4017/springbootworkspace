package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.entity.Reply;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {
        IntStream.rangeClosed(1, 100).forEach(i->{
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .build();
            Board board = Board.builder()
                    .title("title.." + i)
                    .content("content.." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

    @Test
    public void testRead1() {
        Optional<Board> result = boardRepository.findById(100L);

        if (result.isPresent()) {
            Board board = result.get();
            System.out.println(board);
            System.out.println(board.getWriter());
        }
    }

    @Test
    public void testReadWithWriter() {
        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[])result;
        System.out.println(arr[0]);
        System.out.println(arr[1]);
    }

    @Test
    public void testGetBoardWithReply() {
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

//        Object[] arr = result.get(0);
//        Board board = (Board) arr[0];
//        Reply reply = (Reply) arr[1];
//
//        System.out.println(board);
//        System.out.println(reply);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }
}
