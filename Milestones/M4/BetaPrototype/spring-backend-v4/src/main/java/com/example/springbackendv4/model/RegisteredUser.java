package com.example.springbackendv4.model;
import javax.persistence.*;

@Entity
@Table(name = "RegisteredUser")
public class RegisteredUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String user;
    private String firstName;
    private String lastName;
    private String email;
    private String password;


    // ------------------------------------ GETTERS & SETTERS ------------------------------------------- //


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getLastName() {
       return lastName;
   }

   public void setLastName(String lastName) {
       this.lastName = lastName;
   }


    // ------------------------------------ CURRENTLY NOT USED ------------------------------------------- //


//    private Integer storageLeftInBytes = 1000_000_000; //1GB default storage space

//    private String token;

//    public Integer getStorageLeftInBytes() {
//        return storageLeftInBytes;
//    }
//
//    public void setStorageLeftInBytes(Integer storageLeftInBytes) {
//        this.storageLeftInBytes = storageLeftInBytes;
//    }
//
//    public String getToken() {
//        return UUID.randomUUID().toString();
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
}

