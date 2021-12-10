package com.uni.project.entities;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private Integer role;
    @OneToMany
    @JoinColumn(name="publisher_id")
    private List<Listing> listings;
    @OneToMany
    @JoinColumn(name="publisher_id")
    private List<Comment> comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id; }

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

    public Integer getRole() { return role; }

    public  void setRole(Integer role) { this.role = role; }

    public List<Listing> getListings() { return listings; }

    public void setListings(List<Listing> listings) { this.listings = listings; }

    public User() {}
    public User(String username, String password, Integer role) {
        this.setUsername(username);
        this.setPassword(password);
        this.setRole(role);
    }
}
