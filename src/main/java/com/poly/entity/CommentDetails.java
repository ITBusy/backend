package com.poly.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CommentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cDetailsID;
    private String content_reply;
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentDateReply;
    @ManyToOne(fetch = FetchType.EAGER)
    private Comment comment;

    @ManyToOne(fetch = FetchType.EAGER)
    private User userReply;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public CommentDetails() {
    }

    public CommentDetails(Long cDetailsID, String content_reply, Date commentDateReply, Comment comment, User userReply, User user) {
        this.cDetailsID = cDetailsID;
        this.content_reply = content_reply;
        this.commentDateReply = commentDateReply;
        this.comment = comment;
        this.userReply = userReply;
        this.user = user;
    }

    public Long getcDetailsID() {
        return cDetailsID;
    }

    public void setcDetailsID(Long cDetailsID) {
        this.cDetailsID = cDetailsID;
    }

    public String getContent_reply() {
        return content_reply;
    }

    public void setContent_reply(String content_reply) {
        this.content_reply = content_reply;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUserReply() {
        return userReply;
    }

    public void setUserReply(User userReply) {
        this.userReply = userReply;
    }

    public Date getCommentDateReply() {
        return commentDateReply;
    }

    public void setCommentDateReply(Date commentDateReply) {
        this.commentDateReply = commentDateReply;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
