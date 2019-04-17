package com.hlju.mall.dao;

import com.hlju.mall.domain.Comment;
import com.hlju.mall.domain.CommentExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * CommentDAO继承基类
 */
@Repository
@Mapper
public interface CommentDAO extends MyBatisBaseDao<Comment, Integer, CommentExample> {
}