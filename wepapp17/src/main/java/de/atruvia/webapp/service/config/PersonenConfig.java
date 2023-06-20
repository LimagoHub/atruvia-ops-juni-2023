package de.atruvia.webapp.service.config;


import de.atruvia.webapp.persistence.repository.PersonenRepository;
import de.atruvia.webapp.service.PersonenService;
import de.atruvia.webapp.service.internal.PersonenServiceImpl;
import de.atruvia.webapp.service.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;

import java.util.List;

@Configuration
public class PersonenConfig {


    @Bean
    //@Qualifier("antipathen")
    //@Primary
    @Profile("production")
    public List<String> findAntipathen() {
        return List.of("Attila","Peter", "Paul","Mary");
    }

    @Bean
    //@Qualifier("fruits")
    @Profile("test")
    @Scope("prototype")
    public List<String> fruits() {
        return List.of("Apple","Banana", "Cherry","Raspberry");
    }

//    @Bean
//    public PersonenService create(PersonenRepository repo, PersonMapper mapper, List<String> antipathen) {
//        return new PersonenServiceImpl(repo, mapper, antipathen);
//    }
}
