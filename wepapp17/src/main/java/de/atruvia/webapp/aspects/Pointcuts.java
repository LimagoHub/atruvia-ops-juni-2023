package de.atruvia.webapp.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut(value ="execution(public * de.atruvia.webapp.presentation.controller.PersonenCommandController.*(..))")
    public void personenControllerMethods(){}

    @Pointcut(value = "@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerMethods(){}

    @Pointcut("@within(de.atruvia.webapp.aspects.Dozent)")
    public void dozentMethods(){};
}
