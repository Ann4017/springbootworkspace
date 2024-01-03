package org.zerock.ex2.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    private MemoRepository memoRepository;

    @Test
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i->{
            Memo memo = Memo.builder()
                    .memoText("Sample..." + i ).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("================");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
            System.out.println(result);
        }
    }

    @Test
    public void testUpdate() {
        Memo memo = Memo.builder()
                .mno(100L)
                .memoText("update text")
                .build();
        memoRepository.save(memo);

        Long mno = 100L;
        System.out.println(memoRepository.findById(mno));
    }

    @Test
    public void testDelete() {
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault() {
        Pageable pageable = PageRequest.of(0,10);
        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);
        System.out.println("==============");
        System.out.println("total page: " + result.getTotalPages()); // 총 페이지
        System.out.println("page count: " + result.getTotalElements()); // 전체 개수
        System.out.println("page number:" + result.getNumber()); // 현재 페이지 번호 0부터 시작
        System.out.println("page size: " + result.getSize()); // 페이지 당 데이터 개수
        System.out.println("has next page?: " + result.hasNext()); // 다음 페이지 존재 여부
        System.out.println("first page?: " + result.isFirst()); // 시작 페이지(0) 여부

        // getContent()
        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }

    @Test
    public void testSort() {
        Sort sort = Sort.by("mno").descending();

        Pageable pageable = PageRequest.of(0,10,sort);
        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(System.out::println);
    }

    @Test
    public void testQueryMethods() {
        List<Memo> list =  memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPageable() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);

        result.get().forEach(System.out::println);
    }

    @Test
    @Commit
    @Transactional
    public void testDeleteQueryMethod() {
        memoRepository.deleteMemoByMnoLessThan(10L);
    }
}


