package org.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // 생성자 의존 주입 (final 로 정의 되어 있는 멤버 변수를 의존 주입)
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;
    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO log--------------");
        log.info(dto);

        Guestbook entity = dtoTOEntity(dto);
        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        Page<Guestbook> result = repository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> fn = (this::entityToDto);

        return new PageResultDTO<>(result, fn);
    }
}
