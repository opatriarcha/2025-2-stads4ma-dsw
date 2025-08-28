package br.com.senac.sp.stads4ma.dsw.Blog.domainmodel.repositories;


import lombok.*;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
//@AllArgsConstructor
@Builder
public class User {
    private @Setter @Getter UUID id;
    private @Setter @Getter String name;
    private @Setter @Getter String email;
    private @Setter @Getter String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public User(UUID id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
