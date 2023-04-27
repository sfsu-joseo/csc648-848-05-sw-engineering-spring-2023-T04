package main.java.com.example.springbackendv2.model;

import javax.persistence.*;

// NEW TABLE

@Entity
@Table(name = "sharedList")
public class SharedList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sharedListID", nullable = false, unique = true)
    private int sharedListID;

    @Column(name = "url", nullable = false, length = 200)
    private String url;

    @Column(name = "isDownloadable", nullable = false)
    private boolean isDownloadable;

    @ManyToOne
    @JoinColumn(name = "userID")
    private int userID;

    @ManyToOne
    @JoinColumn(name = "resourceID")
    private int resourceID;

    public int getSharedListID() {
        return sharedListID;
    }

    public void setSharedListID(int sharedListID) {
        this.sharedListID = sharedListID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDownloadable() {
        return isDownloadable;
    }

    public void setDownloadable(boolean isDownloadable) {
        this.isDownloadable = isDownloadable;
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

    public void setUserResource(int resourceID) {
        this.resourceID = resourceID;
    }
}
