package org.zerock.ex1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleContorller {

    @GetMapping("/hello")
    public String[] hello() {
        return new String[] {"hello","world"};
    }
}