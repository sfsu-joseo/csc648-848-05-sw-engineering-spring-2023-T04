package main.java.com.example.springbackendv2.model;

import javax.persistence.*;

// NEW TABLE

@Entity
@Table(name = "userNote")
public class UserNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noteID", nullable = false, unique = true)
    private int noteID;

    @Column(name = "note", nullable = false, columnDefinition = "TINYTEXT")
    private String note;

    @ManyToOne
    @JoinColumn(name = "userID")
    private int userID;

    @ManyToOne
    @JoinColumn(name = "resourceID")
    private int resourceID;

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public Note(){}
    public Note(int noteID, String note, int userID) {
        this.noteID = noteID;
        this.note = note;
        this.userID = userID;
    }
}
