package com.HE180030.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "account", schema = "shoes_onlineshopping")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "is_sell")
    private Boolean isSell;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Size(max = 10)
    @Column(name = "username", length = 10)
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;

}