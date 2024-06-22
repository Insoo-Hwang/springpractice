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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class SpringpracticeApplication {

    public static void main(String[] args) {
        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory(); //Tomcat이 아닌 Container 사용 가능
        WebServer webServer = serverFactory.getWebServer(servletContext -> {
            servletContext.addServlet("frontcontroller", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    //인증, 보안, 다국어, 공통 기능 처리
                    if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) { //hello 요청 처리 && GET 방식
                        //======================요청 start======================
                        String name = req.getParameter("name");
                        //=======================요청 end=======================

                        //======================응답 start======================
                        resp.setStatus(HttpStatus.OK.value());
                        resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                        resp.getWriter().println("Hello " + name);
                        //=======================응답 end=======================
                    }
                    else if(req.getRequestURI().equals("/user")) {
                        //user 요청 처리
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
