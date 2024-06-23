package org.example.springpractice;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;

public class SpringpracticeApplication {

    public static void main(String[] args) {
        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext(); //Spring Container
        applicationContext.registerBean(HelloController.class); //Bean 등록
        applicationContext.registerBean(SimpleHelloService.class); //Bean 등록 + HelloController에 의존성 주입(HelloService 인터페이스를 구현한 것 중 유일)
        applicationContext.refresh(); //Bean Object 생성

        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory(); //Tomcat이 아닌 Container 사용 가능
        WebServer webServer = serverFactory.getWebServer(servletContext -> {
            servletContext.addServlet("dispatcherServlet",
                        new DispatcherServlet(applicationContext) //Servlet Container에 Spring Container 정보 전달
                    ).addMapping("/*"); //Path의 모든 요청 처리
        });
        webServer.start(); //Tomcat Servlet Container 실행
    }

}
