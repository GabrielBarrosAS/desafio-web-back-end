package leadmentoring.desafiowebbackend.controller;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.dtos.languageDTOS.LanguagePostDTO;
import leadmentoring.desafiowebbackend.service.LanguageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("language")
@RequiredArgsConstructor
@Log4j2
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping
    public List<Language> listAllNoPageable(){
        return languageService.listAllNoPageable();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Language> findById(@PathVariable long id){
        return new ResponseEntity(languageService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Language> save(@RequestBody @Valid LanguagePostDTO languagePostDTO){
        Language language = languageService.save(languagePostDTO);
        log.info(language);
        return new ResponseEntity(language, HttpStatus.CREATED);
    }

}
