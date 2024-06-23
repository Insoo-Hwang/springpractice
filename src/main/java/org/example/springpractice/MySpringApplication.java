package org.example.springpractice;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MySpringApplication {

    public static void run(Class<?> applicationClass, String... args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(){
            @Override
            protected void onRefresh() {
                super.onRefresh();

                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
                dispatcherServlet.setApplicationContext(this); //생성자 없이 선언하였으므로 주입, Spring Container가 자동으로 주입

                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet", dispatcherServlet) //Servlet Container에 Spring Container 정보 전달
                            .addMapping("/*"); //Path의 모든 요청 처리
                });
                webServer.start(); //Tomcat Servlet Container 실행
            }
        }; //Spring Container
        applicationContext.register(applicationClass); //Bean 등록
        applicationContext.refresh(); //Bean Object 생성
    }
}
