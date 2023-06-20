package de.atruvia.webapp.service.mapper;

import de.atruvia.webapp.persistence.entity.PersonEntity;
import de.atruvia.webapp.service.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    Person convert(PersonEntity entity);
    PersonEntity convert(Person person);

    Iterable<Person> convert(Iterable<PersonEntity> entities);
}
