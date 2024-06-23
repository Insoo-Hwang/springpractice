package org.example.springpractice;

import org.springframework.stereotype.Component;

@MyComponent
public class SimpleHelloService implements HelloService {
    @Override
    public String sayHello(String name){
        return "Hello " + name;
    }
}
