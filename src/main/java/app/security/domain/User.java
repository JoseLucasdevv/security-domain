package app.security.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "the field name cannot be nullish")
    @NotBlank(message = "the field name cannot be Blank")
    private String name;
    @Size(min=3,max=25,message = "your username should be between 3 characters and 25 characters")
    @NotNull(message = "the field username cannot be nullish")
    private String username;
    @NotNull(message = "the field password cannot be nullish")
    private String password;
    @NotNull(message = "the field role cannot be nullish")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Workout> workout = new ArrayList<>();


    @Override
    public String toString() {
        return this.id + " " + this.name;
    }
}
