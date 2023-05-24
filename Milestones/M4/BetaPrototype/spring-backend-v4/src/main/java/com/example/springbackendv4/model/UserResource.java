package com.example.springbackendv4.model;

import javax.persistence.*;

@Entity
@Table(name = "UserResource")
public class UserResource {

    // NOTE: this also includes folders.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long fk_owner_id;
    private String filename;
    private String visibility;
    private String type;
    private String permissions;


    // ------------------------------------ GETTERS & SETTERS ------------------------------------------- //


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFk_owner_id(Long fk_owner_id) {
        this.fk_owner_id = fk_owner_id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getPermissions() {
        return permissions;
    }

    public String getType() {
        return type;
    }

    public Long getFk_owner_id() {
        return fk_owner_id;
    }
}
