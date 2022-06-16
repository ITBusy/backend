package com.poly.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentDate;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @OneToMany(mappedBy = "comment")
    @JsonIgnore
    private Set<CommentDetails> commentDetailsSet = new LinkedHashSet<>();

    public Comment() {
    }

    public Comment(Long commentId, String content, Date commentDate, User user, Product product, Set<CommentDetails> commentDetailsSet) {
        this.commentId = commentId;
        this.content = content;
        this.commentDate = commentDate;
        this.user = user;
        this.product = product;
        this.commentDetailsSet = commentDetailsSet;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Set<CommentDetails> getCommentDetailsSet() {
        return commentDetailsSet;
    }

    public void setCommentDetailsSet(Set<CommentDetails> commentDetailsSet) {
        this.commentDetailsSet = commentDetailsSet;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }
}
