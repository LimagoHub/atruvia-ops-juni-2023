package de.atruvia.webapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class WebappApplication {


    public static void main(String[] args) {
        SpringApplication.run(WebappApplication.class, args);
    }

}
