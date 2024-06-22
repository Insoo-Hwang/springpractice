package org.example.springpractice;

import org.springframework.web.bind.annotation.GetMapping;

public class HelloController {
    public String hello(String name){
        return "Hello" + name;
    }
}
