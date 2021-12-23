package leadmentoring.desafiowebbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.dtos.languageDTOS.LanguagePostDTO;
import leadmentoring.desafiowebbackend.service.LanguageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("language")
@RequiredArgsConstructor
@Log4j2
@ApiResponses(value = {
        @ApiResponse(responseCode = "400",description = "When a parameter is not in valid format"),
        @ApiResponse(responseCode = "401",description = "Invalid credentials"),
        @ApiResponse(responseCode = "403",description = "User does not have access (Not admin)")
})
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping
    @Operation(summary = "Lists all languages that are active in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucessful Operation")
    })
    public ResponseEntity<List<Language>> listAllNoPageable(){

        return new ResponseEntity<>(languageService.listAllNoPageable(),HttpStatus.OK);

    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Search a languages by unique id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "There is element with the specified id"),
            @ApiResponse(responseCode = "404", description = "Element not found")
    })
    public ResponseEntity<Language> findById(@PathVariable long id){
        return new ResponseEntity(languageService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new languages when parameters are passed correctly")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Element is created correctly"),
    })
    public ResponseEntity<Language> save(@RequestBody @Valid LanguagePostDTO languagePostDTO){
        Language language = languageService.save(languagePostDTO);
        log.info(language);
        return new ResponseEntity(language, HttpStatus.CREATED);
    }

}
