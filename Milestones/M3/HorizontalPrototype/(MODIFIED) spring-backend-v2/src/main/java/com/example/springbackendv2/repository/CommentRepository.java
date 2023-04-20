package com.example.springbackendv2.repository;

import com.example.springbackendv2.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c WHERE c.user_id = :userId")
    List<Comment> findAllByUserId(Long userId);

    @Query("select c from Comment c WHERE c.folder_id = :folderId")
    List<Comment> findAllByFolderId(String folderId);

    @Query("delete c from Comment c WHERE c.folder_id = :folderId")
    void deleteByFolderId(String folderId);


    @Query("update Comment c set c.text = :text WHERE c.id = :id")
    void updateCommentTextById(Long id, String text);
}
