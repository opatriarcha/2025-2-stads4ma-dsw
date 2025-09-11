package br.com.senac.sp.stads4ma.dsw.Blog.service;

import br.com.senac.sp.stads4ma.dsw.Blog.domainmodel.repositories.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> findAll();

    User findById(UUID id);

    boolean deleteById(UUID id);

    User create(User user);
}
