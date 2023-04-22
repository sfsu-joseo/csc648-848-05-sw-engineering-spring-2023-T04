package com.example.springbackendv2.service;

import com.example.springbackendv2.model.Comment;

import java.util.List;

public interface CommentService {


    List<Comment> getAllCommentByUserId(Long userId);

    List<Comment> getAllCommentByFolderId(String folderId);

    void deleteByFolderId(String folderId);

    void updateCommentTextById(Long id, String text);

    Comment saveOrUpdateComment(Comment comment);
}
