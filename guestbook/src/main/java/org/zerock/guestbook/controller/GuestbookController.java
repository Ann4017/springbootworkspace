package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;
import org.zerock.guestbook.service.GuestbookServiceImpl;

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
}
