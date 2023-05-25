package com.example.springbackendv4.repository;

import com.example.springbackendv4.model.UserResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserResourceRepository extends JpaRepository<UserResource, Long> {
    @Query(value = "SELECT * FROM user_resource WHERE visibility LIKE 'Public' AND type LIKE 'Folder'", nativeQuery = true)
    List<UserResource> findAllPublicFiles();

    @Query(value = "SELECT * FROM user_resource WHERE REPLACE(filename, ' ', '') LIKE '%'  :query  '%' AND visibility LIKE 'Public' AND type LIKE 'Folder'", nativeQuery = true)
    List<UserResource> searchByFilename(@Param("query") String query);
}
