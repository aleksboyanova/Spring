package com.uni.project.entities;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private User publisher;

    @OneToMany
    @JoinColumn(name="listing_id")
    private List<Comment> comments;
    public Listing(){}

    public Listing(String title, String description){
        this.setTitle(title);
        this.setDescription(description);
    }

    public Listing(String title, String description, User publisher) {
        this(title,description);
        this.setPublisher(publisher);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public List<Comment> getComments() { return comments; }

    public void setComments(List<Comment> comments) { this.comments = comments; }
}
