package de.atruvia.webapp.service;

import de.atruvia.webapp.service.model.Person;

public interface BlacklistService {

    boolean isBlacklisted(Person possibleBlacklisted);
}
