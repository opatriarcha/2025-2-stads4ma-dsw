package br.com.senac.sp.stads4ma.dsw.Blog.presentation;


import br.com.senac.sp.stads4ma.dsw.Blog.domainmodel.repositories.User;
import br.com.senac.sp.stads4ma.dsw.Blog.service.UserService;
import br.com.senac.sp.stads4ma.dsw.Blog.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    //http://localhost:8080/api/users
    @GetMapping
    public ResponseEntity<List<User>> findAllUsers(){
        return ResponseEntity.ok(this.userService.findAll());
    }

//    http://localhost:8080/api/users/1234-12345-1234-12345
    @GetMapping("/{id}")
    public ResponseEntity<User> findAllUsersById(@PathVariable UUID id){
        User user = this.userService.findById(id);
        if( user != null)
            return ResponseEntity.ok(user);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable UUID id){
        if(this.userService.deleteById(id))
            return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User resultUser = this.userService.create(user);
        if(resultUser != null)
            return  new ResponseEntity<>(resultUser, HttpStatus.CREATED);
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        this.userService.update( user );
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<User> partialUpdateUser(@RequestBody User user){
        this.userService.update( user );
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


}
