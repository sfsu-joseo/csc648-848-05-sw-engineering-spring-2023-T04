package com.example.springbackendv2.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

// NEW TABLE

@Entity
@Table(name = "userResource")
public class UserResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resourceID", nullable = false, unique = true)
    private int resourceID;

    @Column(name = "title", nullable = false, unique = true, length = 32)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "uploadDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date uploadDate;

    @Column(name = "courseID", nullable = false, length = 12)
    private String courseID;

    @Column(name = "professorName", nullable = false, length = 32)
    private String professorName;

    @Column(name = "isPrivate", nullable = false)
    private boolean isPrivate;

    @ManyToOne
    @JoinColumn(name = "userID")
    private int userID;

    @ManyToOne
    @JoinColumn(name = "commentID")
    private int commentID;

    @OneToOne
    @JoinColumn(name = "noteID")
    private int noteID;

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

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

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

}

