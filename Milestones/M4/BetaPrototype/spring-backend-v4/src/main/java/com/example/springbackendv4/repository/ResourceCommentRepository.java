package com.example.springbackendv4.repository;

import com.example.springbackendv4.dto.MessengerDto;
import com.example.springbackendv4.model.ResourceComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ResourceCommentRepository extends JpaRepository<ResourceComment, Long> {
    @Query(value = "select c from resource_comments c WHERE c.userID = :userId", nativeQuery = true)
    List<ResourceComment> findAllByUserId(@Param("userId") Long userId);

    @Query(value = "select c from resource_comments c WHERE c.resourceID = :resourceID", nativeQuery = true)
    List<ResourceComment> findAllByResourceId(@Param("resourceID") Long resourceID);

    @Query(value = "delete c from resource_comments c WHERE c.commentID = :commentID", nativeQuery = true)
    void deleteByCommentId(@Param("commentID") Long commentID);


    @Query(value = "update resource_comments c set c.text = :text WHERE c.id = :id", nativeQuery = true)
    void updateCommentTextById(@Param("id")  Long id, @Param("text")  String text);

    @Query(value = """
           select new ResourceCommentRecord(
           c.commendID as commentID,
           c.comment as comment,
           c.commentDate as commentDate,
           u.username as username,
           u.userImage as userImage
           )
           from ResourceComment c
           join c.registeredUser u
           where c.resourceID = :resourceID
           order by c.id
            """, nativeQuery = true)
    List<MessengerDto> findAllResourceCommentDTOByResourceID(@Param("resourceID") int resourceID);


    // ------------------------------------ CURRENTLY IN USE ------------------------------------------- //


    @Transactional
    @Modifying
    @Query(value = "delete c from resource_comments c WHERE c.fk_file_id = :resourceID", nativeQuery = true)
    void deleteByResourceId(@Param("resourceID") Long resourceID);
    @Query(value = "SELECT * FROM resource_comments WHERE fk_file_id LIKE :resourceID ORDER BY comment_date", nativeQuery = true)
    List<ResourceComment> findAllByFileId(@Param("resourceID") Long resourceID);
}
