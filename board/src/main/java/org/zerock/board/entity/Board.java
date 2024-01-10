package org.zerock.board.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;  // 연관 관계 지정 -> Member.email = Board.email

    public void changeTitle(String title) {
        this.title = title;
    }

    public  void changeContent(String content) {
        this.content = content;
    }
}
