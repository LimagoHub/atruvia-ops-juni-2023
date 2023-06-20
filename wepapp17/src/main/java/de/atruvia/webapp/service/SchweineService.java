package de.atruvia.webapp.service;


import de.atruvia.webapp.service.model.Schwein;

import java.util.Optional;

public interface SchweineService {

    boolean speichern(Schwein schwein) throws SchweineServiceException;
    boolean loesche(String id) throws SchweineServiceException;

    Optional<Schwein> findeNachId(String id) throws SchweineServiceException;

    Iterable<Schwein> findeAlle() throws  SchweineServiceException;

    boolean fuettern(String id) throws SchweineServiceException;
}
