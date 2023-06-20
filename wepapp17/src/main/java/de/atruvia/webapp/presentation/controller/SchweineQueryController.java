package de.atruvia.webapp.presentation.controller;


import de.atruvia.webapp.presentation.dto.SchweinDto;
import de.atruvia.webapp.presentation.mapper.SchweinDtoMapper;
import de.atruvia.webapp.service.SchweineService;
import de.atruvia.webapp.service.SchweineServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/schweine")
@Tag(name = "schweine", description = "the schweine API with documentation annotations")
@AllArgsConstructor
public class SchweineQueryController {

    private final SchweineService service;
    private final SchweinDtoMapper mapper;

    @Operation(summary = "Get a person by person id")
    @ApiResponse(responseCode = "200", description = "Person wurde gefunden")
    @ApiResponse(responseCode = "404", description = "Person wurde nicht gefunden" , content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content )
    @ApiResponse(responseCode = "500", description = "Interner Serverfehler", content = @Content)


    @GetMapping(path = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<SchweinDto> findById (
            @Parameter(description = "id of foo to be searched") @PathVariable String id
    ) throws SchweineServiceException{
        return ResponseEntity.of(service.findeNachId(id).map(mapper::convert));
    }
    @ApiResponse(responseCode = "200", description = "Schweineabfrage erfolgreich ausgeführt")
    @ApiResponse(responseCode = "400", description = "Bad Request" )
    @ApiResponse(responseCode = "500", description = "Interner Serverfehler")
    @GetMapping(path = "",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<SchweinDto>> findeAlle(
            @RequestParam(required = false, defaultValue = "") String name
           ) throws SchweineServiceException {
        return ResponseEntity.ok(mapper.convert(service.findeAlle()));
    }

}
