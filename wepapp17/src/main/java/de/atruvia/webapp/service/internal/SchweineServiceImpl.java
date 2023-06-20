package de.atruvia.webapp.service.internal;


import de.atruvia.webapp.persistence.repository.SchweinRepository;
import de.atruvia.webapp.service.SchweineService;
import de.atruvia.webapp.service.SchweineServiceException;
import de.atruvia.webapp.service.mapper.SchweinMapper;
import de.atruvia.webapp.service.model.Schwein;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = SchweineServiceException.class, isolation = Isolation.READ_COMMITTED)
public class SchweineServiceImpl implements SchweineService {

    private final SchweinRepository repo;
    private final SchweinMapper mapper;

    @Override
    public boolean speichern(Schwein schwein) throws SchweineServiceException {
        try {
            boolean result = repo.existsById(schwein.getId());
            repo.save(mapper.convert(schwein));
            return result;
        } catch (Exception e) {
            throw new SchweineServiceException("upps", e);
        }
    }

    @Override
    public Optional<Schwein> findeNachId(String id) throws SchweineServiceException {
        try {
            return repo.findById(id).map(mapper::convert);
        } catch (Exception e) {
            throw new SchweineServiceException("upps", e);
        }
    }

    @Override
    public Iterable<Schwein> findeAlle() throws SchweineServiceException {
        try {
            return mapper.convert(repo.findAll());
        } catch (Exception e) {
            throw new SchweineServiceException("upps", e);
        }
    }

    @Override
    public boolean loesche(String id) throws SchweineServiceException {
        try {
            if( repo.existsById(id)) {
                repo.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new SchweineServiceException("upps", e);
        }
    }

    @Override
    public boolean fuettern(String id) throws SchweineServiceException {

        return findeNachId(id).map(this::futternUndSpeichernSchwein).isPresent();

    }


    private Schwein futternUndSpeichernSchwein(Schwein s) {

            s.fuettern();
            speichern(s);
            return s;

    }
}
