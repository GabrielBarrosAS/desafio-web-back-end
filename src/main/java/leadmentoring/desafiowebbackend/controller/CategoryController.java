package leadmentoring.desafiowebbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.dtos.categoryDTOS.CategoryPostDTO;
import leadmentoring.desafiowebbackend.dtos.categoryDTOS.CategoryPutDTO;
import leadmentoring.desafiowebbackend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "category")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400",description = "When a parameter is not in valid format"),
        @ApiResponse(responseCode = "401",description = "Invalid credentials"),
        @ApiResponse(responseCode = "403",description = "User does not have access (Not admin)")
})
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Lists all categories that are active in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucessful Operation")
    })
    public ResponseEntity<List<Category>> listAllNoPageable(){

        return new ResponseEntity<>(categoryService.listAllNoPageable(),HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Search a category by unique id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "There is element with the specified id"),
            @ApiResponse(responseCode = "404", description = "Element not found")
    })
    public ResponseEntity<Category> findById(@PathVariable long id){
        return new ResponseEntity(categoryService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new category when parameters are passed correctly")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Element is created correctly"),
            @ApiResponse(responseCode = "404", description = "The language is not registered in the database"),
    })
    public ResponseEntity<Category> save(@RequestBody @Valid CategoryPostDTO categoryPostDTO){
        Category category = categoryService.save(categoryPostDTO);
        return new ResponseEntity(category, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a category that already exists in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element is updeted correctly"),
            @ApiResponse(responseCode = "404", description = "The language is not registered in the database"),
            @ApiResponse(responseCode = "401",description = "Invalid credentials"),
    })
    public ResponseEntity<Category> update(@RequestBody @Valid CategoryPutDTO categoryPutDTO){
        return new ResponseEntity(categoryService.update(categoryPutDTO),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a category that already exists in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element is deleted correctly"),
            @ApiResponse(responseCode = "404", description = "Element not found")
    })
    public ResponseEntity<Category> delete(@PathVariable long id){
        return new ResponseEntity(categoryService.delete(id),HttpStatus.OK);
    }

}
