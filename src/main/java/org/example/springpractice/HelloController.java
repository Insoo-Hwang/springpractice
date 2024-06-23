package org.example.springpractice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@Controller //Spring boot 3.0부터 @Controller 명시
@RequestMapping("/hello") //Dispatcher Servlet에 class 레벨 정보 전달(method 레벨까지 파악하기엔 Bean 정보가 너무 많음)
public class HelloController {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping//Dispatcher Servlet에 매핑 정보 전달
    @ResponseBody //String 타입으로 return하면 view를 확인하기 때문에 메시지를 명시적으로 return한다고 표기
    public String hello(String name){
        return helloService.sayHello(Objects.requireNonNull(name)); //null 체크
    }
}
