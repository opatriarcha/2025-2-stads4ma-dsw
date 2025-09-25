package br.com.fiap.tds._tdsq.Library.domainmodel.repositories;

import br.com.fiap.tds._tdsq.Library.domainmodel.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryCustom<User, UUID> {

    public User fetchByEmail(String email);

    public Optional<User> findByIdWithProfileAndPostsCriteria(java.util.UUID id);

    public List<User> findByMinPostsAndNameLikeCriteria(int minPosts, String namePart);

    public List<User> findByMinPostsAndNameLikeQdsl(int minPosts, String namePart);

    public Optional<User> findByIdWithProfileAndPostsQdsl(UUID id);

}

