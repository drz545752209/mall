package com.hlju.mall.service;

import com.hlju.mall.domain.BoComment;

import java.util.List;

public interface CommentService {
    boolean addComment(String commentJson);

    List<BoComment> getCommentBoList(String bizName);
}
