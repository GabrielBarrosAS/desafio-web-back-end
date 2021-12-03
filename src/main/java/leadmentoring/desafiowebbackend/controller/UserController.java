package leadmentoring.desafiowebbackend.controller;

import leadmentoring.desafiowebbackend.domain.Users;
import leadmentoring.desafiowebbackend.dtos.usersDTOS.UsersPutDTO;
import leadmentoring.desafiowebbackend.dtos.usersDTOS.UsersPostDTO;
import leadmentoring.desafiowebbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Users> listAllNoPageable(){
        return userService.listAllNoPageable();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Users> findById(@PathVariable long id){
        return new ResponseEntity(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Users> save(@RequestBody @Valid UsersPostDTO usersPostDTO){
        Users user = userService.save(usersPostDTO);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Users> update(@RequestBody @Valid UsersPutDTO usersPutDTO){
        return new ResponseEntity(userService.update(usersPutDTO),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Users> delete(@PathVariable long id){
        return new ResponseEntity(userService.delete(id),HttpStatus.OK);
    }
}
