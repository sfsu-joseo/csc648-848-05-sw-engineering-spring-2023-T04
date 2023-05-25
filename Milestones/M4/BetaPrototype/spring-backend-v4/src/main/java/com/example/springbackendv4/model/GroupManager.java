package com.example.springbackendv4.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "GroupManager")
public class GroupManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Temporal(TemporalType.DATE)
    private Date last_updated;

    private Long fk_owner_id;


    // ------------------------------------ GETTERS & SETTERS ------------------------------------------- //


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFk_owner_id() {
        return fk_owner_id;
    }

    public void setFk_owner_id(Long fk_owner_id) {
        this.fk_owner_id = fk_owner_id;
    }

    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }
}
