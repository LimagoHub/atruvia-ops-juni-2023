package de.atruvia.webapp.presentation.controller;

import de.atruvia.webapp.presentation.dto.PersonDto;
import de.atruvia.webapp.presentation.mapper.PersonDtoMapper;
import de.atruvia.webapp.service.PersonenService;
import de.atruvia.webapp.service.PersonenServiceException;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/personen")
@RequiredArgsConstructor

public class PersonenQueryController {

    private final PersonenService service;
    private final PersonDtoMapper mapper;
    // Swagger
    @Operation(summary = "Get a person by person id")
    @ApiResponse(responseCode = "200", description = "Person wurde gefunden")
    @ApiResponse(responseCode = "404", description = "Person wurde nicht gefunden" , content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content )
    @ApiResponse(responseCode = "500", description = "Interner Serverfehler", content = @Content)


    // Spring Rest
    @GetMapping(path="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})

    public ResponseEntity<PersonDto> findPersonById(@PathVariable  String id) throws PersonenServiceException {
        return ResponseEntity.of(service.findeNachId(id).map(mapper::convert));
    }

    @Operation(summary = "Get all persons")
    @ApiResponse(responseCode = "200", description = "Ok")
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content )
    @ApiResponse(responseCode = "500", description = "Interner Serverfehler", content = @Content)

    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<PersonDto>> findeAlle(
            @RequestParam(required = false, defaultValue = "") String vorname,
            @RequestParam(required = false, defaultValue = "") String nachname) throws PersonenServiceException{
        System.out.println(String.format("Vorname = %s, nachname = %s", vorname, nachname));


        return ResponseEntity.ok(mapper.convert(service.findeAlle()));
    }



//    @PostMapping("/functions/toupper")
//    public PersonDto toUpper(@RequestBody  PersonDto dto) {
//        dto.setNachname(dto.getNachname().toUpperCase());
//        return dto;
//    }

}
