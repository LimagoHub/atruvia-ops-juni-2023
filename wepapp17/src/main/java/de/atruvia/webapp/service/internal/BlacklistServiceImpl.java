package de.atruvia.webapp.service.internal;

import de.atruvia.webapp.service.BlacklistService;
import de.atruvia.webapp.service.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BlacklistServiceImpl implements BlacklistService {

    private static final List<String> ANTIPATHEN = List.of("Attila", "Peter");
    @Override
    public boolean isBlacklisted(final Person possibleBlacklisted) {
        return ANTIPATHEN.contains(possibleBlacklisted.getVorname());
    }
}
