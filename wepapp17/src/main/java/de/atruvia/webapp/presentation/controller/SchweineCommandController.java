package de.atruvia.webapp.presentation.controller;


import de.atruvia.webapp.presentation.dto.SchweinDto;
import de.atruvia.webapp.presentation.mapper.SchweinDtoMapper;
import de.atruvia.webapp.service.SchweineService;
import de.atruvia.webapp.service.SchweineServiceException;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.geo.Metric;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;



@RestController
@RequestMapping("/v1/schweine")
@RequiredArgsConstructor
@Timed("Schwein")
@Tag(name = "schweine", description = "the schweine API with documentation annotations")
public class SchweineCommandController {

    private final SchweineService service;
    private final SchweinDtoMapper mapper;
    
    private final MeterRegistry registry;
    private AtomicInteger meinGauge;
    
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
    
    @PostConstruct
    public void init() {
        meinGauge = registry.gauge("MeinGauge", new AtomicInteger(0));
    }

    @Timed(value = "schwein.saveorupdate", description = "Ein Schwein wurde gespeichert oder aktualisiert")
    @PutMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveOrUpdate(@Valid @RequestBody SchweinDto dto, UriComponentsBuilder builder) throws SchweineServiceException{
        // save
        UriComponents uriComponent = builder.path("/v1/schweine/{id}").buildAndExpand(dto.getId());
        if(service.speichern(mapper.convert(dto)))
            return ResponseEntity.ok().build();
        return ResponseEntity.created(uriComponent.toUri()).build();
    }

    @Timed(value = "schwein.fuettern", description = "Ein Schwein wurde gefuettert")
    @Operation(summary = "Das Schwein mit der gegebenen ID wird gefuettert und aendert sein Gewicht")
    @PutMapping(path="/{id}/fuettern")
    public ResponseEntity<Void> futtern(
            @PathVariable String id,
            @RequestParam(required = true) int anzahlKartoffel
            ) throws SchweineServiceException{

        meinGauge.set(anzahlKartoffel);
        if(service.fuettern(id))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
