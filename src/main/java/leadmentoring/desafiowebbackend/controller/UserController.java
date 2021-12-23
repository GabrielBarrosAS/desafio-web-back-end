package leadmentoring.desafiowebbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import leadmentoring.desafiowebbackend.domain.Users;
import leadmentoring.desafiowebbackend.dtos.usersDTOS.UsersPostDTO;
import leadmentoring.desafiowebbackend.dtos.usersDTOS.UsersPutDTO;
import leadmentoring.desafiowebbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lists all users that are active in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucessful Operation")
    })
    public ResponseEntity<List<Users>> listAllNoPageable(){

        return new ResponseEntity<>(userService.listAllNoPageable(),HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Search a users by unique id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "There is element with the specified id"),
            @ApiResponse(responseCode = "404", description = "Element not found")
    })
    public ResponseEntity<Users> findById(@PathVariable long id){
        return new ResponseEntity(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new users when parameters are passed correctly")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Element is created correctly"),
            @ApiResponse(responseCode = "404", description = "The language is not registered in the database"),
            @ApiResponse(responseCode = "400",description = "When a parameter is not in valid format")
    })
    public ResponseEntity<Users> save(@RequestBody @Valid UsersPostDTO usersPostDTO){
        Users user = userService.save(usersPostDTO);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update a users that already exists in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element is updeted correctly"),
            @ApiResponse(responseCode = "404", description = "The language is not registered in the database"),
            @ApiResponse(responseCode = "401",description = "Invalid credentials"),
            @ApiResponse(responseCode = "403",description = "When a user tries to update another user"),
            @ApiResponse(responseCode = "400",
                    description = "When the new email or cpf already belongs to another user")
    })
    public ResponseEntity<Users> update(@RequestBody @Valid UsersPutDTO usersPutDTO,
                                        @ParameterObject @AuthenticationPrincipal UserDetails userDetails){

        return new ResponseEntity(userService.update(usersPutDTO,userDetails),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a users that already exists in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element is deleted correctly"),
            @ApiResponse(responseCode = "404", description = "Element not found")
    })
    public ResponseEntity<Users> delete(@PathVariable long id){

        return new ResponseEntity(userService.delete(id),HttpStatus.OK);
    }
}
