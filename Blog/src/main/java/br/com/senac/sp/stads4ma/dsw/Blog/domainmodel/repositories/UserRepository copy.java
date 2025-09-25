package br.com.fiap.tds._tdsq.Library.domainmodel.repositories;

import br.com.fiap.tds._tdsq.Library.domainmodel.Post;
import br.com.fiap.tds._tdsq.Library.domainmodel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends
        JpaRepository<User, UUID>,
        QuerydslPredicateExecutor<User>,
        UserRepositoryCustom<User, UUID>{

    //Method Derived query
    List<User> findByEmail(String email);
    List<User> findByNameAndEmail(String name, String email);
    List<User> findByPosts(List<Post> post);

    //JPQL - Java Persistence Query Language
    @Query("SELECT u FROM User u WHERE u.name = :name")
    Optional<User> findByName(String name);

    // Q1 (JPQL): busca o usuário por ID já fazendo fetch de profile e posts
    @Query("""
           select distinct u
           from User u
           left join fetch u.profile
           left join fetch u.posts
           where u.id = :id
           """)
    Optional<User> findByIdWithProfileAndPosts(@Param("id") UUID id);

    // Q2 (JPQL): usuários cujo nome contém (case-insensitive) e com pelo menos N posts
    @Query("""
           select u
           from User u
           where size(u.posts) >= :minPosts
             and lower(u.name) like lower(concat('%', :namePart, '%'))
           order by u.name asc
           """)
    List<User> findByMinPostsAndNameLike(@Param("minPosts") int minPosts,
                                         @Param("namePart") String namePart);


 }
