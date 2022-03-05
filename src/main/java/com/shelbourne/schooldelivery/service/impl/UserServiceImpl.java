package com.shelbourne.schooldelivery.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shelbourne.schooldelivery.common.States;
import com.shelbourne.schooldelivery.common.Result;
import com.shelbourne.schooldelivery.controller.dto.UserDTO;
import com.shelbourne.schooldelivery.entity.User;
import com.shelbourne.schooldelivery.exception.ServiceException;
import com.shelbourne.schooldelivery.mapper.UserMapper;
import com.shelbourne.schooldelivery.service.IUserService;
import com.shelbourne.schooldelivery.utils.TokenUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Shelby
 * @since 2022-02-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    @Override
    public Result login(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(States.USER_EXCEPTION, "用户名或密码为空！");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        if (list(queryWrapper).size() == 0) {//用户名未注册
            return Result.error(States.USER_EXCEPTION, "登录失败，用户未注册！");
        }
        queryWrapper.eq("password", password);
//        方式一:
        try {
            User one = getOne(queryWrapper);
            if (one != null) {
//                UserDTO ret = new UserDTO();
                //把数据库里查到的one对象部分属性copy到ret返回给前端:忽略字段大小写且不传递password（lombok插件的原因（或许）使得该方法无法生效）
//                BeanUtil.copyProperties(one, ret, new CopyOptions().ignoreCase().setIgnoreProperties("password"));
//                System.out.println(one);
//                System.out.println(ret);
                //BeanUtil.copyProperties(one, userDTO, true);//把数据库里查到的one对象部分属性copy到userDTO返回给前端(该方法暂时无法实现
                //手动赋值
                //userDTO.setNickname(one.getNickname());
                //userDTO.setAvatarUrl(one.getAvatarUrl());
                //userDTO.setPassword("");//登录成功后隐藏用户密码
                // ret.setPassword("");
                UserDTO ret = new UserDTO(one.getId(),
                        one.getUsername(),
                        one.getNickname(),
                        one.getAddress(),
                        one.getPhone(),
                        one.getEmail(),
                        one.getAvatarUrl(),
                        TokenUtils.getToken(one.getId().toString(), one.getPassword()));
                return Result.success(States.SUCCESS, "登录成功！", ret);
            } else {
                //抛出ServiceException异常，会被GlobalExceptionHandler类捕获
                throw new ServiceException(States.USER_EXCEPTION, "用户名或密码错误！");
            }
        } catch (ServiceException e) {
            return Result.error(e.getState(), e.getMessage());
        } catch (Exception e) {
//            数据库里用户名和密码不唯一时捕获异常
            LOG.error(e);
            return Result.error(States.DATABASE_EXCEPTION, "数据库错误，用户数据不唯一！");
        }
//        方式二
//        List<User> list = list(queryWrapper);
//        return list.size() != 0;该
    }

    @Override
    public Result register(User user) {
        String username = user.getUsername();
        if (StrUtil.isBlank(username)) {
            return Result.error(States.USER_EXCEPTION, "用户名不能为空！");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        try {
            User one = getOne(queryWrapper);
            if (one != null) {
                return Result.error(States.USER_EXCEPTION, "用户名已存在！");
            } else {
                if (save(user)) {
                    return Result.success(States.SUCCESS, "注册成功！");
                } else {
                    return Result.error(States.UNKNOWN_ERROR, "保存用户失败！");
                }
            }
        } catch (Exception e) {
//            数据库里用户名和密码不唯一时捕获异常
            LOG.error(e);
            return Result.error(States.DATABASE_EXCEPTION, "数据库错误，数据不唯一！");
        }
    }
}
