package main.java.com.example.springbackendv2.model;

import javax.persistence.*;

@Entity
@Table(name = "userFriend")
public class UserFriend {

    @Id
    @ManyToOne
    @JoinColumn(name = "UID1", referencedColumnName = "userID")
    private int user1;

    @Id
    @ManyToOne
    @JoinColumn(name = "UID2", referencedColumnName = "userID")
    private int user2;

    public UserFriend() {
    }

    public UserFriend(int user1, int user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public int getUser1() {
        return user1;
    }

    public void setUser1(int user1) {
        this.user1 = user1;
    }

    public int getUser2() {
        return user2;
    }

    public void setUser2(int user2) {
        this.user2 = user2;
    }
}
