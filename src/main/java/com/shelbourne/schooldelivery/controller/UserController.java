package com.shelbourne.schooldelivery.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shelbourne.schooldelivery.entity.User;
import com.shelbourne.schooldelivery.mapper.UserMapper;
import com.shelbourne.schooldelivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")  //统一给接口加前缀，postman后台接口localhost:9090/user
@RestController
public class UserController {

//    @Autowired  //注入其他类的注解
//    private UserMapper userMapper;

    @Autowired
    private UserService userService;

//    @GetMapping
//    public List<User> findAll() {
//        List<User> all = userMapper.findAll();
//        return all;
//    }

    //查询所有用户
    @GetMapping
    public List<User> findAll(String username) {
        return userService.list();
    }

    //通过POST请求进行新增和更新操作
    @PostMapping
    public boolean save(@RequestBody User user) {//一定要加上RequestBody，可以把前端传回的JSON对象转换为Java对象
        return userService.saveOrUpdate(user);
    }

    //删除请求接口
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {//这里的“id”必须和DeleteMapping里面的名字一样
        return userService.removeById(id);
    }

    //批量删除请求接口
    @PostMapping("/batchDel")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {//这里的“id”必须和DeleteMapping里面的名字一样
        return userService.removeBatchByIds(ids);
    }


    //分页查询接口，路径：/user/page
    //@RequestParam接收：?pageNum=1&pageSize=10   其中pageNum为页面的起始记录，pageSize为记录条数
//    @GetMapping("/page")
//    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
//        //分页原理：select * from user limit startIdx,size;其中startIdx = 开始记录下标（0开始），size = 记录条数
//        //换算：startIdx = (pageNum-1)*pageSize   size = pageSize
//        int startIdx = (pageNum - 1) * pageSize, size = pageSize;
//        List<User> data = userMapper.selectPage(startIdx, size);//获取一页的数据
//        int total = userMapper.selectTotal();//查询总条数
//        Map<String, Object> res = new HashMap<>();
//        res.put("data", data);//表格数据
//        res.put("total", total);//分页使用
//        return res;
//    }

    //分页+条件查询
//    @GetMapping("/page")
//    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String username) {
//        int startIdx = (pageNum - 1) * pageSize, size = pageSize;
//        List<User> data = userMapper.selectPageWithUsername(startIdx, size, "%" + username + "%");//获取一页的数据
//        int total = userMapper.selectTotalWithUsername(username);//查询总条数
//        Map<String, Object> res = new HashMap<>();
//        res.put("data", data);//表格数据
//        res.put("total", total);//分页使用
//        return res;
//    }

    //分页+条件查询
//    @GetMapping("/page")
//    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String username,
//                                        @RequestParam String email, @RequestParam String address) {
//        int startIdx = (pageNum - 1) * pageSize, size = pageSize;
//        List<User> data = userMapper.selectPageWithParam(startIdx, size, username, email, address);//获取一页的数据
//        int total = userMapper.selectTotalWithParam(username, email, address);//查询总条数
//        Map<String, Object> res = new HashMap<>();
//        res.put("data", data);//表格数据
//        res.put("total", total);//分页使用
//        return res;
//    }

    //条件查询+分页
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("username", username);//like之间是and关系
//        queryWrapper.like("email", email);
//        queryWrapper.like("address", address);
//        queryWrapper.like("username", username).like("email", email).like("address", address);

        if (!"".equals(username)) {//加条件判断可以查询null值
            queryWrapper.like("username", username);
        }
        if (!"".equals(email)) {
            queryWrapper.like("email", email);
        }
        if (!"".equals(address)) {
            queryWrapper.like("address", address);
        }
        queryWrapper.orderByDesc("id");
        return userService.page(page, queryWrapper);
    }
}
