package org.zerock.extest.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("hello")
    public String[] test() {
        return new String[]{"ggg", "hhh"};
    }
}
