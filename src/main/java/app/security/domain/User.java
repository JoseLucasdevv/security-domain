package app.security.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;
    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Workout> workout = new ArrayList<>();

}
