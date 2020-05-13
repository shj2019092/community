package com.shj.community.service;

import com.shj.community.mapper.UserMapper;
import com.shj.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
     User dbuser=   userMapper.findByAccountId(user.getAccountId());
     if(dbuser==null){
         //插入
         user.setGmtCreate(System.currentTimeMillis());
         user.setGmtModified(user.getGmtCreate());
         userMapper.insertUser(user);
     }else {
         //更新
         dbuser.setGmtModified(System.currentTimeMillis());
         dbuser.setAvatarUrl(user.getAvatarUrl());
         dbuser.setName(user.getName());
         dbuser.setToken(user.getToken());
         userMapper.update(dbuser);

     }

    }
}
