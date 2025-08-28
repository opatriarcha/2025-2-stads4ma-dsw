package br.com.senac.sp.stads4ma.dsw.Blog.domainmodel.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {

    private List<User> internalDataset = new ArrayList<>();

    public UserRepository(){
        Faker faker = new Faker();
        for( int i = 0; i < 100; i++){

            User user = new User( UUID.randomUUID(),
                    faker.name().fullName(),
                    faker.internet().emailAddress(),
                    faker.internet().password(8,16)
            );
            this.internalDataset.add(user);
        }

    }

    public List<User> findAll() {
        return this.internalDataset.stream().toList();
    }
}
