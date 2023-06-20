package de.atruvia.webapp.presentation.controller;


import de.atruvia.webapp.presentation.dto.SchweinDto;
import de.atruvia.webapp.presentation.mapper.SchweinDtoMapper;
import de.atruvia.webapp.service.SchweineService;
import de.atruvia.webapp.service.SchweineServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;



@RestController
@RequestMapping("/v1/schweine")
@AllArgsConstructor
@Tag(name = "schweine", description = "the schweine API with documentation annotations")
public class SchweineCommandController {

    private final SchweineService service;
    private final SchweinDtoMapper mapper;

    @ApiResponse(responseCode = "200", description = "Schwein erfolgreich gelöscht")
    @ApiResponse(responseCode = "404", description = "Schwein wurde nicht gefunden" )
    @ApiResponse(responseCode = "400", description = "Bad Request" )
    @ApiResponse(responseCode = "500", description = "Interner Serverfehler")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> remove(
            @PathVariable String id


    ) throws SchweineServiceException {

        if(service.loesche(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveOrUpdate(@Valid @RequestBody SchweinDto dto, UriComponentsBuilder builder) throws SchweineServiceException{
        // save
        UriComponents uriComponent = builder.path("/v1/schweine/{id}").buildAndExpand(dto.getId());
        if(service.speichern(mapper.convert(dto)))
            return ResponseEntity.ok().build();
        return ResponseEntity.created(uriComponent.toUri()).build();
    }

    @Operation(summary = "Das Schwein mit der gegebenen ID wird gefuettert und aendert sein Gewicht")
    @PutMapping(path="/{id}/fuettern")
    public ResponseEntity<Void> futtern(
            @PathVariable String id,
            @RequestParam(required = true) int anzahlKartoffel
            ) throws SchweineServiceException{

        if(service.fuettern(id))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
