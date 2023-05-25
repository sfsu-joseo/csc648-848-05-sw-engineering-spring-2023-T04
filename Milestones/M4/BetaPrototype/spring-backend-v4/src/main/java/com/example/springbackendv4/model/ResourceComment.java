package com.example.springbackendv4.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "ResourceComments")
public class ResourceComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Temporal(TemporalType.DATE)
    private Date comment_date;

    private String comment;

    private Long fk_file_id;

    private Long fk_user_id;


    // ------------------------------------ GETTERS & SETTERS ------------------------------------------- //


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getFk_file_id() {
        return fk_file_id;
    }

    public void setFk_file_id(Long fk_file_id) {
        this.fk_file_id = fk_file_id;
    }

    public Long getFk_user_id() {
        return fk_user_id;
    }

    public void setFk_user_id(Long fk_user_id) {
        this.fk_user_id = fk_user_id;
    }
}
