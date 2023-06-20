package de.atruvia.webapp.service.internal;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.atruvia.webapp.aspects.Dozent;
import de.atruvia.webapp.persistence.repository.PersonenRepository;
import de.atruvia.webapp.service.PersonenService;
import de.atruvia.webapp.service.PersonenServiceException;
import de.atruvia.webapp.service.mapper.PersonMapper;
import de.atruvia.webapp.service.model.Person;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.RequiredArgsConstructor;

@Dozent
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = PersonenServiceException.class, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)


public class PersonenServiceImpl implements PersonenService {
    
    private final Counter sympathenCounter = Metrics.counter("sympathen.counter");
    private final Counter antipathenCounter = Metrics.counter("antipathen.counter");


    private final PersonenRepository repository;
    private final PersonMapper mapper;
    private final List<String> antipathen;

    /*
        person == null -> PSE
        vorname == null -> PSE
        VORNAME zu kurz -> PSE
        nachname == null -> PSE
        nachNAME zu kurz -> PSE

        Attila -> PSE

        wenn exception im repo -> PSE

        happy day -> person an repo weiterreichen

     */
    @Override
    @Counted("anlegen.counter")
    public void anlegen(final Person person) throws PersonenServiceException {
        pruefenUndSpeichern(person, "Fehler beim Anlegen");
    }

    private void pruefenUndSpeichern(final Person person, final String Fehler_beim_Anlegen) throws PersonenServiceException {
        try {
            if (person == null)
                throw new PersonenServiceException("Person darf nicht null sein.");

            if (person.getVorname() == null || person.getVorname().length() < 2)
                throw new PersonenServiceException("Vorname zu kurz.");

            if (person.getNachname() == null || person.getNachname().length() < 2)
                throw new PersonenServiceException("Nachname zu kurz.");

            if (antipathen.contains(person.getVorname())) {
                antipathenCounter.increment();
                throw new PersonenServiceException("Unerwuenschte Person");
            } else {
                sympathenCounter.increment();
            }

            repository.save(mapper.convert(person));
        } catch (RuntimeException e) {
            throw new PersonenServiceException(Fehler_beim_Anlegen, e);
        }
    }

    @Override
    public void aendern(final Person person) throws PersonenServiceException {
        pruefenUndSpeichern(person, "Fehler beim Aendern");
    }

    @Override
    public boolean loesche(String id) throws PersonenServiceException {
        try {
            if(repository.existsById(id)) {
                repository.deleteById(id);
                return true;
            }
            return false;
        } catch (RuntimeException e) {
            throw new PersonenServiceException(e);
        }
    }

    // Select * from customers with ur
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<Person> findeNachId(String id) throws PersonenServiceException {
        try {
            return repository.findById(id).map(mapper::convert);
        } catch (RuntimeException e) {
            throw new PersonenServiceException(e);
        }
    }

    @Override
    public Iterable<Person> findeAlle() throws PersonenServiceException {
        try {
            return mapper.convert(repository.findAll());
        } catch (RuntimeException e) {
            throw new PersonenServiceException(e);
        }
    }
}
