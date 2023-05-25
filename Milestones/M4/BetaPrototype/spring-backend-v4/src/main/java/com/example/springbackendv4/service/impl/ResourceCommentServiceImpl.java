package com.example.springbackendv4.service.impl;

import com.example.springbackendv4.model.ResourceComment;
import com.example.springbackendv4.repository.ResourceCommentRepository;
import com.example.springbackendv4.service.ResourceCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceCommentServiceImpl implements ResourceCommentService {

    @Autowired
    ResourceCommentRepository repository;

    @Override
    public List<ResourceComment> getAllCommentByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public List<ResourceComment> getAllCommentDtoByResourceId(Long resourceId) {
        return repository.findAllByFileId(resourceId);
    }

    @Override
    public List<ResourceComment> getAllCommentByResourceId(Long resourceId) {
        return repository.findAllByResourceId(resourceId);
    }

    @Override
    public void deleteByResourceId(Long resourceId) {
        repository.deleteByResourceId(resourceId);
    }

    @Override
    public void deleteByCommentId(Long commentId) {
        repository.deleteByCommentId(commentId);
    }

    @Override
    public void updateCommentTextById(Long id, String text) {
        repository.updateCommentTextById(id, text);
    }

    @Override
    public ResourceComment saveOrUpdateComment(ResourceComment comment) {
        return repository.save(comment);
    }
}
