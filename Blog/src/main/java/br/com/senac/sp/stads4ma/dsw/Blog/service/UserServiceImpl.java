package br.com.senac.sp.stads4ma.dsw.Blog.service;

import br.com.senac.sp.stads4ma.dsw.Blog.domainmodel.repositories.User;
import br.com.senac.sp.stads4ma.dsw.Blog.domainmodel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> resultset = new ArrayList<>();
        resultset.addAll(this.userRepository.findAll());
        return resultset;
    }
}
