package com.hlju.mall.service.impl;

import com.hlju.mall.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Override
    @Transactional
    public boolean addComment(String commentJson) {

        return false;
    }
}
