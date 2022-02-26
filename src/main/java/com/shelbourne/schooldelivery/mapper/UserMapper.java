package com.shelbourne.schooldelivery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shelbourne.schooldelivery.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

//mapper和数据库打交道
//mapper层的方法不能重载！！！
//@Mapper //注入mapper接口，如果使用了MapperScan则不需要
public interface UserMapper extends BaseMapper<User> {
    //查询所有用户
//    @Select("select * from user")
    //mybatis提供注解，注意SQL语句后不能加分号
//    List<User> findAll();

    //新增用户
//    @Insert("insert into user(username,password,nickname,email,phone,address)" +
//            "values(#{username},#{password},#{nickname},#{email},#{phone},#{address})")
//    public int insert(User user);

    //通过注解（静态）和xml里面（动态）两种方式编写SQL语句
//    int update(User user);

    //删除单个用户
//    @Delete("delete from user where id=#{id}")
//    Integer deleteById(@Param("id") Integer id);//最后加上@Param参数，参数名和上面的#{}里面的一样

    //查询记录条数
//    @Select("select count(*) from user")
//    Integer selectTotal();

    //分页查询
//    @Select("select * from user limit #{startIdx},#{size}")
//    List<User> selectPage(@Param("startIdx") Integer startIdx, @Param("size") Integer size);

    //分页查询+条件查询
//    @Select("select * from user where username like #{username} limit #{startIdx},#{size}")
//    @Select("select * from user where username like concat('%',#{username},'%') limit #{startIdx},#{size}")
//    List<User> selectPageWithUsername(@Param("startIdx") Integer startIdx, @Param("size") Integer size, @Param("username") String username);

    //查询记录条数+条件查询
//    @Select("select count(*) from user where username like concat('%',#{username},'%')")
//    Integer selectTotalWithUsername(@Param("username") String username);

    //编写动态SQL实现分页查询+条件查询
    //查询满足条件的某一页用户
//    List<User> selectPageWithParam(Integer startIdx, Integer size, String username, String email, String address);

    //查询满足条件的所有用户数
//    int selectTotalWithParam(String username, String email, String address);

}
