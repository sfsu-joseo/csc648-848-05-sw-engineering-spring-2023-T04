package main.java.com.example.springbackendv2.model;

import javax.persistence.*;

// NEW TABLE

@Entity
@Table(name = "registeredUser")
public class RegisteredUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", nullable = false, unique = true)
    private int userID;

    @Column(name = "username", nullable = false, unique = true, length = 32)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 32)
    private String password;

    @Column(name = "formalName", nullable = false, length = 32)
    private String formalName;

    @Column(name = "userImage", nullable = true, columnDefinition = "blob")
    private byte[] userImage;

    // !! Not sure if this should be in registeredUser table, commented for now !!
    // @ManyToOne
    // @JoinColumn(name = "sharedListID")
    // private int sharedListID;

    // @ManyToOne
    // @JoinColumn(name = "resourceID")
    // private int resourceID;

    // @ManyToOne
    // @JoinColumn(name = "commentID")
    // private int commentID;

    @OneToMany(mappedBy = "user")
    private List<Note> notes;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFormalName() {
        return formalName;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }
    
    // !! Not sure if this should be in registeredUser table, commented for now !!
    // public SharedList getSharedListID() {
    //     return sharedListID;
    // }

    // public void setSharedListID(int sharedListID) {
    //     this.sharedListID = sharedListID;
    // }

    // public int getResourceID() {
    //     return resourceID;
    // }

    // public void setResourceID(int resourceID) {
    //     this.resourceID = resourceID;
    // }

    // public int getResourceID() {
    //     return resourceID;
    // }

    // public void setResourceID(int resourceID) {
    //     this.resourceID = resourceID;
    // }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }
}
