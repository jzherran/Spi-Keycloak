package com.payulatam.keycloak.storage.user;

import javax.persistence.*;

/**
 * @author <a href="mailto:jhonatan.zambrano@payulatam.com">Jhonatan A. Zambrano</a>
 *         12/10/2016
 */

@NamedQueries({
        @NamedQuery(name="getUserByUsername", query="select u from PayUUserEntity u where u.email = :email"),
        @NamedQuery(name="getUserByEmail", query="select u from PayUUserEntity u where u.email = :email"),
        @NamedQuery(name="getUserCount", query="select count(u) from PayUUserEntity u"),
        @NamedQuery(name="getAllUsers", query="select u from PayUUserEntity u"),
        @NamedQuery(name="searchForUser", query="select u from PayUUserEntity u where " +
                "( lower(u.email) like :search or u.email like :search ) order by u.email"),
})

@Entity
@Table(name="usuario")
public class PayUUserEntity {

    @Id
    private String id;

    @Column(name = "nombres")
    private String firstname;
    @Column(name = "apellidos")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
