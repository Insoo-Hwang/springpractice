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

import java.io.IOException;

public class SpringpracticeApplication {

    public static void main(String[] args) {
        GenericApplicationContext applicationContext = new GenericApplicationContext(); //Spring Container
        applicationContext.registerBean(HelloController.class); //Bean 등록
        applicationContext.registerBean(SimpleHelloService.class); //Bean 등록 + HelloController에 의존성 주입(HelloService 인터페이스를 구현한 것 중 유일)
        applicationContext.refresh(); //Bean Object 생성

        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory(); //Tomcat이 아닌 Container 사용 가능
        WebServer webServer = serverFactory.getWebServer(servletContext -> {
            servletContext.addServlet("frontcontroller", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    //인증, 보안, 다국어, 공통 기능 처리
                    if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) { //hello 요청 처리 && GET 방식
                        //======================요청 start======================
                        String name = req.getParameter("name"); //queryString에서 추출
                        //=======================요청 end=======================
                        HelloController helloController = applicationContext.getBean(HelloController.class); //Bean을 가져옴
                        String ret = helloController.hello(name); //hello+name 문자열 생성
                        //======================응답 start======================
                        resp.setContentType(MediaType.TEXT_PLAIN_VALUE); //header 지정
                        resp.getWriter().println(ret);
                        //=======================응답 end=======================
                    }
                    else {
                        resp.setStatus(HttpStatus.NOT_FOUND.value()); //나머지 페이지는 404 error
                    }
                }
            }).addMapping("/*"); //Path의 모든 요청 처리
        });
        webServer.start(); //Tomcat Servlet Container 실행
    }

}
