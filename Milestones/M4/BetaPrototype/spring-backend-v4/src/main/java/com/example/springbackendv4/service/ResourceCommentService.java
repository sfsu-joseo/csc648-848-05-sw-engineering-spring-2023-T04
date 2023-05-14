package com.example.springbackendv4.service;


import com.example.springbackendv4.dto.ResourceCommentRecord;
import com.example.springbackendv4.model.ResourceComment;

import java.util.List;

public interface ResourceCommentService {


    List<ResourceComment> getAllCommentByUserId(Long userId);

    List<ResourceComment> getAllCommentByResourceId(int resourceID);

    List<ResourceCommentRecord> getAllCommentDtoByResourceId(int resourceID);

    void deleteByResourceId(int resourceID);

    void deleteByCommentId(Long commentId);

    void updateCommentTextById(Long id, String text);

    ResourceComment saveOrUpdateComment(ResourceComment comment);
}
