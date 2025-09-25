package br.com.senac.sp.stads4ma.dsw.Blog.domainmodel.repositories;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
//@AllArgsConstructor
@Builder
@Entity
@Table(name = "SYSTEM_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private @Setter @Getter UUID id;

    @Column(name = "NAME", length = 255)
    private @Setter @Getter String name;

    @Column(name = "EMAIL", length = 60, unique = true)
    private @Setter @Getter String email;

    @Column(name = "PASSWORD", length = 20)
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

    public User(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
