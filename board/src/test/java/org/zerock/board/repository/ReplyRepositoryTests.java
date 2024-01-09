package org.zerock.board.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {
        IntStream.rangeClosed(1, 300).forEach(i->{
            Long bno = (long) (Math.random() * 100) + 1;
            Board board = Board.builder()
                    .bno(bno)
                    .build();
            Reply reply = Reply.builder()
                    .text("text.." + i)
                    .replyer("guest")
                    .board(board)
                    .build();

            replyRepository.save(reply);
        });
    }

    @Test
    @Transactional
    public void readReply1() {
        Optional<Reply> result = replyRepository.findById(1L);

        if (result.isPresent()) {
            Reply reply = result.get();
            System.out.println(reply);
            System.out.println(reply.getBoard());
        }
    }
}
