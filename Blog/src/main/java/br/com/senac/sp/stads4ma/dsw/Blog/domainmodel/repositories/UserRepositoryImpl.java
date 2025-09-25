package br.com.fiap.tds._tdsq.Library.domainmodel.repositories;

import br.com.fiap.tds._tdsq.Library.domainmodel.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.Setter;
import br.com.fiap.tds._tdsq.Library.domainmodel.QUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public class UserRepositoryImpl implements UserRepositoryCustom<User, UUID> {

    @PersistenceContext
    private @Setter EntityManager entityManager;

    @Override
    public User fetchByEmail(String email) {
        JPAQueryFactory factory = new JPAQueryFactory(entityManager);
        QUser user = QUser.user;

        JPAQuery<User> query = factory
                .select(user)
                .where(
                        user.email.eq(email)
                );
        return query.fetchFirst();
    }

    @Override
    public Optional<User> findByIdWithProfileAndPostsCriteria(UUID id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> root = criteriaQuery.from(User.class);
        // fetch joins para evitar N+1 e carregar associações
        Fetch<User, ?> profileFetch = root.fetch("profile", JoinType.LEFT);
        Fetch<User, ?> postsFetch   = root.fetch("posts", JoinType.LEFT);

        criteriaQuery.select(root).distinct(true)
                .where(criteriaBuilder.equal(root.get("id"), id));

        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        List<User> results = query.getResultList();

        return results.stream().findFirst();
    }

    @Override
    public List<User> findByMinPostsAndNameLikeCriteria(int minPosts, String namePart) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> root = criteriaQuery.from(User.class);

        // equivalente lógico de: size(u.posts) >= :minPosts
        // Observação: criteriaBuilder.size(root.get("posts")) é suportado pela JPA;
        // se preferir máxima portabilidade, pode-se usar join + groupBy/having (ver comentário abaixo).
        criteriaQuery.select(root)
                .where(
                        criteriaBuilder.and(
                                criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.size(root.get("posts")), minPosts),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + namePart.toLowerCase() + "%")
                        )
                )
                .orderBy(criteriaBuilder.asc(root.get("name")));

        return entityManager.createQuery(criteriaQuery).getResultList();

        /*
        // Alternativa (mais "portável"): join + groupBy/having (substitui o criteriaBuilder.size)
        var posts = root.join("posts", JoinType.LEFT);
        criteriaQuery.select(root)
          .groupBy(root.get("id"), root.get("name"), root.get("email"), root.get("password"))
          .having(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.count(posts), (long) minPosts))
          .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + namePart.toLowerCase() + "%"))
          .orderBy(criteriaBuilder.asc(root.get("name")));
        return em.createQuery(criteriaQuery).getResultList();
        */
    }

    private JPAQueryFactory qf() {
        return new JPAQueryFactory(this.entityManager);
    }

    @Override
    public Optional<User> findByIdWithProfileAndPostsQdsl(UUID id) {
        QUser u = QUser.user;
        QProfile p = QProfile.profile;
        QPost post = QPost.post;

        // select distinct u with fetch-joins para evitar N+1 e carregar associações
        List<User> result = qf()
                .selectFrom(u)
                .leftJoin(u.profile, p).fetchJoin()
                .leftJoin(u.posts, post).fetchJoin()
                .where(u.id.eq(id))
                .distinct()
                .fetch();

        return result.stream().findFirst();
    }

    @Override
    public List<User> findByMinPostsAndNameLikeQdsl(int minPosts, String namePart) {
        QUser u = QUser.user;
        QPost post = QPost.post;

        // join + groupBy + having count(posts) >= :minPosts
        // filtro por nome case-insensitive com containsIgnoreCase
        return qf()
                .select(u)
                .from(u)
                .leftJoin(u.posts, post)
                .where(u.name.containsIgnoreCase(namePart))
                .groupBy(u.id) // (Hibernate aceita agrupar por id do agregado)
                .having(post.id.count().goe((long) minPosts))
                .orderBy(u.name.asc())
                .fetch();
    }
}
