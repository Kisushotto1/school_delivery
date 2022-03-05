package com.shelbourne.schooldelivery.service;

import com.shelbourne.schooldelivery.common.Result;
import com.shelbourne.schooldelivery.controller.dto.UserDTO;
import com.shelbourne.schooldelivery.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Shelby
 * @since 2022-02-27
 */
public interface IUserService extends IService<User> {
    Result login(UserDTO userDto);

    Result register(User user);
}
