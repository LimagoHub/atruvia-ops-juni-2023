package de.atruvia.webapp.service.mapper;


import de.atruvia.webapp.persistence.entity.SchweinEntity;
import de.atruvia.webapp.service.model.Schwein;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchweinMapper {

    Schwein convert(SchweinEntity entity);
    SchweinEntity convert(Schwein schwein);

    Iterable<Schwein> convert(Iterable<SchweinEntity> entities);
}
