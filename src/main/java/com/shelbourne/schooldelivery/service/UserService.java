package com.shelbourne.schooldelivery.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shelbourne.schooldelivery.entity.User;
import com.shelbourne.schooldelivery.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//service层通过调用mapper，以实现各种服务
@Service //把service类注入springboot容器中，否则springboot找不到这个类，导致无法使用
public class UserService extends ServiceImpl<UserMapper,User> {
//    @Autowired  //通过Autowired引入UserMapper
//    private UserMapper userMapper;//通过UserMapper实现数据库的新增和更新

//    public int save(User user){
//        if(null == user.getId()){//新增
//            return userMapper.insert(user);
//        }else{//更新
//            return userMapper.update(user);
//        }
//    }

}
