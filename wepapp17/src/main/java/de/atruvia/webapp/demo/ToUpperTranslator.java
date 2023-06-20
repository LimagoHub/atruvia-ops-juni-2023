package de.atruvia.webapp.demo;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



@Component
//@Qualifier("upper")
@Profile("production")
@Scope("prototype")
public class ToUpperTranslator implements Translator{

    public ToUpperTranslator() {
        System.out.println("ToUpper");
    }

    @Override
    public String translate(final String message) {
        return message.toUpperCase();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("post construct ToUpper");
    }

    @PreDestroy // Nur bei singleton!!!!
    public void preDestroy() {
        System.out.println("pre destroy ToUpper");
    }
}
