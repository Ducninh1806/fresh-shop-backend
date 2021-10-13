package com.ducninh.freshfood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @Size( max = 255)
    private String firstName;

    @Column(name = "last_name")
    @Size( max = 255)
    private String lastName;

    @Column(name = "birth_day")
    private Instant birthDay;

    @Column(name = "address")
    private String address;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "username")
    private String username;

    @Email
    @NaturalId
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "status")
    private Integer status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User (String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
