package de.atruvia.webapp.presentation.mapper;

import de.atruvia.webapp.presentation.dto.PersonDto;
import de.atruvia.webapp.service.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonDtoMapper {
    PersonDto convert(Person person);
    Person convert(PersonDto dto);

    Iterable<PersonDto> convert(Iterable<Person> personen);
}
