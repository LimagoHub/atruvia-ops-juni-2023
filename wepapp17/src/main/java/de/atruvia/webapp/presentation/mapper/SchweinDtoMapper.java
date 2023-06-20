package de.atruvia.webapp.presentation.mapper;


import de.atruvia.webapp.presentation.dto.SchweinDto;
import de.atruvia.webapp.service.model.Schwein;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchweinDtoMapper {

    Schwein convert(SchweinDto schwein);
    SchweinDto convert(Schwein schwein);

    Iterable<SchweinDto> convert(Iterable<Schwein> schweine);
}
