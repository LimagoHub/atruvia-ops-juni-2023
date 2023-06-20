package de.atruvia.webapp.service;

import de.atruvia.webapp.service.model.Person;

import java.util.Optional;

public interface PersonenService {

    void anlegen(Person person) throws PersonenServiceException;

    void aendern(Person person) throws PersonenServiceException;
    boolean loesche(String id) throws PersonenServiceException;

    Optional<Person> findeNachId(String id) throws PersonenServiceException;

    Iterable<Person> findeAlle() throws PersonenServiceException;
}
