package cardio.cardio.entity;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String displayname;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_authority", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "user_id") }, inverseJoinColumns = {
                    @JoinColumn(name = "authority_name", referencedColumnName = "authority_name") })
    private Set<Authority> authorities;
}