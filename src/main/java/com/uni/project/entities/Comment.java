package com.uni.project.entities;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String text;

    @ManyToOne
    private Listing listing;

    @ManyToOne
    private User publisher;
    @OneToOne
    @JoinColumn(name="parent_id") //добавяне на подкоментари към коментари - няма дизайн
    private Comment parent;
    @OneToMany(mappedBy = "parent")
    private List<Comment> replies;

    public Comment() {}

    public Comment(String text, User publisher, Listing listing) {
        this.setText(text);
        this.setPublisher(publisher);
        this.setListing(listing);
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public User getPublisher() { return publisher; }

    public void setPublisher(User publisher) { this.publisher = publisher; }

    public List<Comment> getReplies() { return replies; }

    public void setReplies(List<Comment> replies) { this.replies = replies; }

    public Comment getParent() { return parent; }

    public void setParent(Comment parent) { this.parent = parent; }

    public Listing getListing() { return listing; }

    public void setListing(Listing listing) { this.listing = listing; }
}
