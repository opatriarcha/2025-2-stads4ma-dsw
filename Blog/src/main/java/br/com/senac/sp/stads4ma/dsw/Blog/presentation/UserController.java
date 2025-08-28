package br.com.senac.sp.stads4ma.dsw.Blog.presentation;


import br.com.senac.sp.stads4ma.dsw.Blog.domainmodel.repositories.User;
import br.com.senac.sp.stads4ma.dsw.Blog.service.UserService;
import br.com.senac.sp.stads4ma.dsw.Blog.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers(){
        return ResponseEntity.ok(this.userService.findAll());
    }


}
