package com.keukentafelprototype.collector;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomCardCollector {

    @RequestMapping(value = "/random")
    public static String welcome() {
        return "Welcome motherfucker";
    }
}
