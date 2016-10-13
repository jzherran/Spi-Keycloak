package com.payulatam.keycloak.storage.user;

import javax.persistence.*;

/**
 * MOL User entity
 * Created by cindy.pacheco on 10/10/2016.
 */
@NamedQueries({
        @NamedQuery(name="getUserByUsername", query="select u from UserEntity u where u.username = :username"),
        @NamedQuery(name="getUserByEmail", query="select u from UserEntity u where u.email = :email"),
        @NamedQuery(name="getUserCount", query="select count(u) from UserEntity u"),
        @NamedQuery(name="getAllUsers", query="select u from UserEntity u"),
        @NamedQuery(name="searchForUser", query="select u from UserEntity u where " +
                "( lower(u.username) like :search or u.email like :search ) order by u.username"),
})
@Entity
@Table(name = "usuario")
public class UserEntity {

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private String id;
    @Column(name = "email", insertable = false, updatable = false)
    private String username;
    @Column(name = "email", insertable = false, updatable = false)
    private String email;
    @Column(name = "password", insertable = false, updatable = false)
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
