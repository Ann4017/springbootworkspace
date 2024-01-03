package org.zerock.ex2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_memo") // 테이블 명 (기본값 은 클래스 이름)
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Memo {
    @Id // 기본키 설정 (primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // seq 설정 (AUTO_INCREMENT)
    private Long mno;

    @Column(length = 200, nullable = false) // 컬럼의 조건 설정
    private String memoText;
}
