package main.java.com.example.springbackendv2.model;

import javax.persistence.*;

@Entity
@Table(name = "userFriend")
public class UserFriend {

    @Id
    @ManyToOne
    @JoinColumn(name = "UID1", referencedColumnName = "userID")
    private int UID1;

    @Id
    @ManyToOne
    @JoinColumn(name = "UID2", referencedColumnName = "userID")
    private int UID2;

    public getUserFriend() {
    }

    public setUserFriend(int UID1, int UID2) {
        this.UID1 = UID1;
        this.UID2 = UID2;
    }

    public int getUID1() {
        return UID1;
    }

    public void setUID1(int UID1) {
        this.UID1 = UID1;
    }

    public int getUID2() {
        return UID2;
    }

    public void setUID2(int UID2) {
        this.UID2 = UID2;
    }
}
