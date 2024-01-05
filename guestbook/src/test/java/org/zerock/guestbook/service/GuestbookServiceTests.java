package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister() {

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("T title")
                .content("T content")
                .writer("T writer")
                .build();
        System.out.println(service.register(guestbookDTO));
    }

    @Test
    public void testList() {
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(requestDTO);

        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }
    }

    @Test
    public void testList2() {
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(requestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }

        System.out.println("=============================");

        resultDTO.getPageList().forEach(System.out::println);
    }
}
