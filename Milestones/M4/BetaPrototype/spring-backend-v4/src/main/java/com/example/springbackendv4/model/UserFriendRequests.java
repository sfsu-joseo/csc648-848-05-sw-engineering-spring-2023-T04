package com.example.springbackendv4.model;

import javax.persistence.*;

@Entity
@Table(name = "UserFriendRequests")
public class UserFriendRequests {

    //    NOTE: generic user data... should be updated later
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long fk_sender_id;
    private Long fk_receiver_id;


    // ------------------------------------ GETTERS & SETTERS ------------------------------------------- //


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFk_sender_id() {
        return fk_sender_id;
    }

    public void setFk_sender_id(Long fk_sender_id) {
        this.fk_sender_id = fk_sender_id;
    }

    public Long getFk_receiver_id() {
        return fk_receiver_id;
    }

    public void setFk_receiver_id(Long fk_receiver_id) {
        this.fk_receiver_id = fk_receiver_id;
    }
}