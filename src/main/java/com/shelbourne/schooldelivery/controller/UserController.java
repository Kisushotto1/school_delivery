package com.shelbourne.schooldelivery.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shelbourne.schooldelivery.common.States;
import com.shelbourne.schooldelivery.common.Result;
import com.shelbourne.schooldelivery.controller.dto.UserDTO;
import com.shelbourne.schooldelivery.entity.User;
import com.shelbourne.schooldelivery.service.IUserService;
import com.shelbourne.schooldelivery.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/*
UserController:class -> IUserService:interface -> UserServiceImp:class
 */

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Shelby
 * @since 2022-02-27
 */
@RestController //可以返回JSON和视图  @Controller只能返回视图  @ResponseBody只能返回JSON
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

    //查询所有
    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public Result find(@PathVariable Integer id) {
        User user = userService.getById(id);
        if (user != null) {
            return Result.success(States.SUCCESS, "查询成功！", new UserDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getNickname(),
                    user.getAddress(),
                    user.getPhone(),
                    user.getEmail(),
                    user.getAvatarUrl(),
                    TokenUtils.getToken(user.getId().toString(), user.getPassword())));
        }
        return Result.error(States.USER_EXCEPTION, "未找到用户！");
    }

    //新增或删除接口
    @PostMapping
    public Result save(@RequestBody User user) {
        boolean r = userService.saveOrUpdate(user);
        if (r) {
            return Result.success(States.SUCCESS, "保存成功！");
        }
        return Result.error(States.SERVICE_EXCEPTION, "保存失败");
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

    //简单条件+分页查询
    @GetMapping("/page")
    public Result findPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "") String address) {

        //获取当前用户信息
//        User user = TokenUtils.getCurrentUser();
//        System.out.println(user);

        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //根据用户名搜索
        if (!"".equals(username)) {//加条件判断可以查询null值
            queryWrapper.like("username", username);
        }

        //根据邮箱搜索
        if (!"".equals(email)) {//加条件判断可以查询null值
            queryWrapper.like("email", email);
        }
        //根据地址搜索
        if (!"".equals(address)) {//加条件判断可以查询null值
            queryWrapper.like("address", address);
        }
        // 根据id倒序
        queryWrapper.orderByDesc("id");
        return Result.success(States.SUCCESS, "查询成功！", userService.page(page, queryWrapper));
    }

    //导出接口，export接收一个response对象
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        //从数据库查出所有数据
        List<User> list = userService.list();
        // ExcelWriter excelWriter = ExcelUtil.getWriter("diskpath");//直接写到磁盘路径
        //写出到浏览器
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        // 自定义标题别名，在实体类的属性上加了hutool的Alias注解则不需要
        //excelWriter.addHeaderAlias("id", "ID");
        //excelWriter.addHeaderAlias("username", "用户名");
        //excelWriter.addHeaderAlias("nickname", "昵称");
        //excelWriter.addHeaderAlias("password", "密码");
        //excelWriter.addHeaderAlias("address", "地址");
        //excelWriter.addHeaderAlias("phone", "电话");
        //excelWriter.addHeaderAlias("email", "邮箱");
        //excelWriter.addHeaderAlias("createTime", "创建时间");
        //excelWriter.addHeaderAlias("avatarUrl", "头像");

        //  一次性写出list内的所有对象到excel，使用默认样式，强制输出标题
        excelWriter.write(list, true);
        // 设置浏览器响应的格式
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//        String fileName = URLEncoder.encode("用户信息", StandardCharsets.UTF_8);
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        // 获取并绑定输出流
        ServletOutputStream outputStream = response.getOutputStream();
        excelWriter.flush(outputStream, true);

        //关闭流对象和ExcelWriter对象
        outputStream.close();
        excelWriter.close();
    }

    //导入接口，imprt接收一个MultipartFile对象
    @PostMapping("/import")
    public boolean impt(MultipartFile file) {
        // 获取文件输入流
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //List<User> list = reader.readAll(User.class);//表头必须是英文
        //跳过表头0，数据开始于1
        List<User> users = reader.read(0, 1, User.class);

        userService.saveBatch(users);
        return true;

    }

    //登录接口 @RequestBody可以把前端传回的JSON转换成JAVA对象
    @PostMapping("/login")
    private Result login(@RequestBody UserDTO userDto) {
        return userService.login(userDto);
    }

    //注册接口
    @PostMapping("/register")
    private Result register(@RequestBody User user) {
        return userService.register(user);
    }
}