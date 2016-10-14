package com.payulatam.keycloak.storage.user;

import org.hibernate.annotations.GenericGenerator;

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

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "nombres")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "apellidos")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
