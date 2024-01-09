package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.service.GuestbookService;
import org.zerock.guestbook.service.GuestbookServiceImpl;

import java.util.Optional;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping({"/"})
    public String index() {
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list") // guestbook/list.html
    public void list(PageRequestDTO requestDTO, Model model) {
        // getList(PageRequestDTO requestDTO)

        log.info("List..............." + requestDTO);

        model.addAttribute("result", service.getList(requestDTO));
    }

    @GetMapping("/register")
    public void register() {

        log.info("GET register");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes) {

        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);

        log.info("POST register");
        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {

        log.info("gno: " + gno);

        GuestbookDTO dto = service.read(gno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/remove")
    public String remove(Long gno, RedirectAttributes redirectAttributes) {

        log.info("gno: " + gno);

        service.remove(gno);
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {

        log.info("post modify.......");
        log.info("dto: " + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("gno", dto.getGno());
        redirectAttributes.addAttribute( "page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        return "redirect:/guestbook/read";
    }
}
