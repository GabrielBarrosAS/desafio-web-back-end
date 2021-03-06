package leadmentoring.desafiowebbackend.controller;

import leadmentoring.desafiowebbackend.domain.Category;
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
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> listAllNoPageable(){
        return categoryService.listAllNoPageable();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable long id){
        return new ResponseEntity(categoryService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> save(@RequestBody @Valid CategoryPostDTO categoryPostDTO){
        Category category = categoryService.save(categoryPostDTO);
        return new ResponseEntity(category, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> update(@RequestBody @Valid CategoryPutDTO categoryPutDTO){
        return new ResponseEntity(categoryService.update(categoryPutDTO),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> delete(@PathVariable long id){
        return new ResponseEntity(categoryService.delete(id),HttpStatus.OK);
    }

}
