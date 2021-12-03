package leadmentoring.desafiowebbackend.controller;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Movies;
import leadmentoring.desafiowebbackend.dtos.moviesDTOS.MoviesPostDTO;
import leadmentoring.desafiowebbackend.dtos.moviesDTOS.MoviesPutDTO;
import leadmentoring.desafiowebbackend.service.MoviesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
public class MoviesController {

    private final MoviesService moviesService;

    @GetMapping
    public List<Movies> listAllNoPageable(){
        return moviesService.listAllNoPageable();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Movies> findById(@PathVariable long id){
        return new ResponseEntity(moviesService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Movies> save(@RequestBody @Valid MoviesPostDTO moviesPostDTO){
        Movies movies = moviesService.save(moviesPostDTO);
        return new ResponseEntity(movies, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Movies> update(@RequestBody @Valid MoviesPutDTO moviesPutDTO){
        return new ResponseEntity(moviesService.update(moviesPutDTO),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Category> delete(@PathVariable long id){
        return new ResponseEntity(moviesService.delete(id),HttpStatus.OK);
    }

}
