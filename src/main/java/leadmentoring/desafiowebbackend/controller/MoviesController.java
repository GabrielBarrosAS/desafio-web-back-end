package leadmentoring.desafiowebbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Movies;
import leadmentoring.desafiowebbackend.dtos.moviesDTOS.MoviesPostDTO;
import leadmentoring.desafiowebbackend.dtos.moviesDTOS.MoviesPutDTO;
import leadmentoring.desafiowebbackend.service.MoviesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Invalid credentials"),
        @ApiResponse(responseCode = "403", description = "User does not have access (Not admin)"),
        @ApiResponse(responseCode = "400", description = "When a parameter is not in valid format"),
})
@SecurityRequirement(name = "Movie System")
public class MoviesController {

    private final MoviesService moviesService;

    @GetMapping
    @Operation(summary = "Lists all movies that are active in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucessful Operation")
    })
    public ResponseEntity<List<Movies>> listAllNoPageable(){

        return new ResponseEntity<>(moviesService.listAllNoPageable(),HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Search a movies by unique id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "There is element with the specified id"),
            @ApiResponse(responseCode = "404", description = "Element not found")
    })
    public ResponseEntity<Movies> findById(@PathVariable long id){
        return new ResponseEntity(moviesService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new movies when parameters are passed correctly")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Element is created correctly"),
            @ApiResponse(responseCode = "404",
                    description = "The language ou category is not registered in the database"),

    })
    public ResponseEntity<Movies> save(@RequestBody @Valid MoviesPostDTO moviesPostDTO){
        Movies movies = moviesService.save(moviesPostDTO);
        return new ResponseEntity(movies, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a movies that already exists in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element is updeted correctly"),
            @ApiResponse(responseCode = "404",
                    description = "The language or category is not registered in the database"),
    })
    public ResponseEntity<Movies> update(@RequestBody @Valid MoviesPutDTO moviesPutDTO){
        return new ResponseEntity(moviesService.update(moviesPutDTO),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a moviesx that already exists in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element is deleted correctly"),
            @ApiResponse(responseCode = "404", description = "Element not found")
    })
    public ResponseEntity<Category> delete(@PathVariable long id){
        return new ResponseEntity(moviesService.delete(id),HttpStatus.OK);
    }

}
