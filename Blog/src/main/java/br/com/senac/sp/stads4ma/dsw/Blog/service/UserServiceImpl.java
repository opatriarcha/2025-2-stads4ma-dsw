package br.com.senac.sp.stads4ma.dsw.Blog.service;

import br.com.senac.sp.stads4ma.dsw.Blog.domainmodel.repositories.User;
import br.com.senac.sp.stads4ma.dsw.Blog.domainmodel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public List<User> findAll() {
        List<User> resultset = new ArrayList<>();
        resultset.addAll(this.userRepository.findAll());
        return resultset;
    }

    @Override
    public User findById(UUID id) {
        return this.userRepository.findById(id);
    }

    @Override
    public boolean deleteById(UUID id) {
        return this.userRepository.removeById(id);
    }

    @Override
    public User create(User user) {
        user.setId(UUID.randomUUID());
        return this.userRepository.create(user);
    }
}
