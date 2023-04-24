package com.example.springbackendv2.repository;

import com.example.springbackendv2.dto.ResourceCommentRecord;
import com.example.springbackendv2.model.ResourceComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceCommentRepository extends JpaRepository<ResourceComment, Long> {

    @Query("select c from ResourceComment c WHERE c.userID = :userId")
    List<ResourceComment> findAllByUserId(Long userId);

    @Query("select c from ResourceComment c WHERE c.resourceID = :resourceID")
    List<ResourceComment> findAllByResourceId(int resourceID);

    @Query("delete c from ResourceComment c WHERE c.resourceID = :resourceID")
    void deleteByResourceId(int resourceID);

    @Query("delete c from ResourceComment c WHERE c.commentID = :commentID")
    void deleteByCommentId(Long commentID);


    @Query("update ResourceComment c set c.text = :text WHERE c.id = :id")
    void updateCommentTextById(Long id, String text);

    @Query("""
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
            """)
    List<ResourceCommentRecord> findAllResourceCommentDTOByResourceID(int resourceID);
}
