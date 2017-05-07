package com.gengbo.service;

import com.gengbo.mapper.UserMapper;
import com.gengbo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;
    
    public boolean addUser(String username, String password){
        return userMapper.insertUser(username, password)==1?true:false;
    }
    
    public User addUserWithBackId(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userMapper.insertUserWithBackId(user);//该方法后，主键已经设置到user中了
        return user;
    }

    public User getUserByIdAndUsernameOrPassword(Integer id, String username, String password){
        User user = userMapper.getUserByIdAndUsernameOrPassword(id, username, password);
        LOGGER.info("getUserByIdAndUsernameOrPassword success! user:'{}'", user);
        return user;
    }
}