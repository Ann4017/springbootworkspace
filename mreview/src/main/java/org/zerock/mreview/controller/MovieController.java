package org.zerock.mreview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.service.MovieService;
import org.zerock.mreview.service.MovieServiceImpl;

@Controller
@Log4j2
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieServiceImpl movieService;

    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String register(MovieDTO movieDTO, RedirectAttributes redirectAttributes) {

        log.info("movieDTO : " + movieDTO);

        Long mno = movieService.register(movieDTO);

        redirectAttributes.addFlashAttribute("msg", mno);

        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("List...................");

        model.addAttribute("result", movieService.getList(pageRequestDTO));
    }

    @GetMapping("/read")
    public void read(MovieDTO movieDTO, PageRequestDTO requestDTO, Model model) {

        log.info("mno.................: " + movieDTO.getMno());
        log.info("page................: " + requestDTO.getPage());

        model.addAttribute("dto", movieService.getMovie(movieDTO.getMno()));
    }

    @GetMapping("/list/{keyword}")
    public void search(PageRequestDTO pageRequestDTO, Model model, @PathVariable String keyword) {

        log.info("--------------------------" + keyword);

        model.addAttribute("result", movieService.getSearch(pageRequestDTO, keyword));

    }
}
