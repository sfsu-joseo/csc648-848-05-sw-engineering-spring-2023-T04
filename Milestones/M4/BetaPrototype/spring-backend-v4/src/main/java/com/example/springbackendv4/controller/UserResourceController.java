package com.example.springbackendv4.controller;

import com.example.springbackendv4.exception.UserNotFoundException;
import com.example.springbackendv4.model.UserResource;
import com.example.springbackendv4.repository.ResourceCommentRepository;
import com.example.springbackendv4.repository.UserResourceRepository;
import com.example.springbackendv4.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UserResourceController {

    @Autowired
    private UserResourceRepository userResourceRepository;
    @Autowired
    private StorageService service;

    @Autowired
    ResourceCommentRepository resourceCommentRepository;

    //   --------------------- BASIC POST, GET, DELETE MAPPING BY UNIQUE ID --------------------

    @PostMapping("/postFile/{owner_id}/{filename}/{type}")
    Long postFile(@PathVariable("owner_id") Long owner_id,
                  @PathVariable("filename") String filename,
                  @PathVariable("type") String type){

//        TODO: restrict filename length here

        UserResource fileMetadata = new UserResource();

        fileMetadata.setFk_owner_id(owner_id); // used for accessing a user's root directory from bucket.
        fileMetadata.setFilename(filename); // <- used for searching
        fileMetadata.setVisibility("Private"); // <- denotes whether this file can be searched.
        fileMetadata.setPermissions("Only you have access"); // <- allows for downloading from search area
        fileMetadata.setType(type); // allows for downloading as zip folder or file, and proper rendering.


        userResourceRepository.save(fileMetadata);
        return fileMetadata.getId();
    }

    @GetMapping("/searchFolder/{query}")
    List<UserResource> searchUser(@PathVariable String query)
    {
        String newQuery = query.replace(" ", "");
        List<UserResource> result = userResourceRepository.searchByFilename(newQuery);
        if(result.isEmpty()) {
            return null;
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(result).getBody();
        }
    }

    @GetMapping("/getAllFiles")
    List<UserResource> getAllFiles(){
        return userResourceRepository.findAllPublicFiles();
    }

    @GetMapping("/file/{id}")
    UserResource getFileById(@PathVariable Long id){
        return  userResourceRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    @PutMapping("/file/{id}")
    UserResource updateFile(@RequestBody UserResource newUserResource, @PathVariable Long id){

        return userResourceRepository.findById(id)
                .map(resource -> {
                    resource.setFilename(newUserResource.getFilename());
                    resource.setVisibility(newUserResource.getVisibility());

                    return userResourceRepository.save(resource);
                }).orElseThrow(()-> new UserNotFoundException(id));
    }

    @DeleteMapping("/file/{id}")
    String deleteFileById(@PathVariable Long id){

        resourceCommentRepository.deleteByResourceId(id);


        if(!userResourceRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }

        userResourceRepository.deleteById(id);



        return "Deleted file with id: " + id;
    }
}
