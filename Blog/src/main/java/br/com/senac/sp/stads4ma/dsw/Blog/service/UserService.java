package br.com.senac.sp.stads4ma.dsw.Blog.service;

import br.com.senac.sp.stads4ma.dsw.Blog.domainmodel.repositories.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
}
