package com.hlju.mall.service;

import com.hlju.mall.domain.BoComment;
import com.hlju.mall.domain.Comment;

import java.util.List;

public interface CommentService {
    boolean addComment(String commentJson);

    List<BoComment> getCommentBoList(String bizName,Integer limit, Long offset);

    Integer getCommentCountByBizName(String bizName);

    Integer getCommentCount(Integer productId);

    List<BoComment> getProductCommentList(Integer productId);
}
