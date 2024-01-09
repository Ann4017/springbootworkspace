package org.zerock.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.IContext;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.Optional;
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

        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);

        Function<Guestbook, GuestbookDTO> fn = (this::entityToDto);

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {

        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void modify(GuestbookDTO dto) {

        Optional<Guestbook> result = repository.findById(dto.getGno());

        if (result.isPresent()) {
            Guestbook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }

    @Override
    public void remove(Long gno) {

        repository.deleteById(gno);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {

        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestbook qGuestbook = QGuestbook.guestbook;

        BooleanExpression expression = qGuestbook.gno.gt(0L); // gno > 0
        booleanBuilder.and(expression);

        if (type == null || type.trim().isEmpty()) { // 검색 조건이 없는 경우
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);// gno > 0 and (t or c or w)

        return booleanBuilder;
    }
}
