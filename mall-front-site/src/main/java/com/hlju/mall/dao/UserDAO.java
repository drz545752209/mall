package com.hlju.mall.dao;

import com.hlju.mall.domain.User;
import com.hlju.mall.domain.UserExample;

/**
 * UserDAO继承基类
 */
public interface UserDAO extends MyBatisBaseDao<User, Integer, UserExample> {
}