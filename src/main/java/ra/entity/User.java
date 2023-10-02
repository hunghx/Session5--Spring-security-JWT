package ra.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private  String password;
    @Column(unique = true)
    private String email;
    private boolean status;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles=new HashSet<>();
}
