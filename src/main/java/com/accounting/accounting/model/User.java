package com.accounting.accounting.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstname; 
    
    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private boolean enabled;

        @CreationTimestamp
    @Column(name = "創建時間", updatable = false)
    private Date createTime;

    @UpdateTimestamp
    @Column(name = "修改時間")
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() { 
        return email;
    }

    public void setEmail(String email) { 
        this.email = email;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
