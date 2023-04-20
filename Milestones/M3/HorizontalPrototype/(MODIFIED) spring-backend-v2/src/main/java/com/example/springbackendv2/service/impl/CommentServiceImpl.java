package com.example.springbackendv2.service.impl;

import com.example.springbackendv2.model.Comment;
import com.example.springbackendv2.repository.CommentRepository;
import com.example.springbackendv2.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository repository;

    @Override
    public List<Comment> getAllCommentByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public List<Comment> getAllCommentByFolderId(String folderId) {
        return repository.findAllByFolderId(folderId);
    }

    @Override
    public void deleteByFolderId(String folderId) {
        repository.deleteByFolderId(folderId);
    }

    @Override
    public void updateCommentTextById(Long id, String text) {
        repository.updateCommentTextById(id, text);
    }

    @Override
    public Comment saveOrUpdateComment(Comment comment) {
        return repository.save(comment);
    }
}
