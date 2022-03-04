package com.shelbourne.schooldelivery.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Shelby
 * @since 2022-02-27
 */
//swagger测试接口2
@ApiModel(value = "User对象", description = "User对象测试接口")

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Alias("ID") //导入文件所需
    private Integer id;

    @TableField(value = "username")
    @Alias("用户名")
    private String username;

    @TableField(value = "password")
    @Alias("密码")
    private String password;

    @TableField(value = "nickname")
    @Alias("昵称")
    private String nickname;

    @TableField(value = "email")
    @Alias("邮箱")
    private String email;

    @TableField(value = "phone")
    @Alias("电话")
    private String phone;

    @TableField(value = "address")
    @Alias("地址")
    private String address;

    @TableField(value = "create_time")
    @Alias("创建时间")
    private LocalDateTime createTime;

    @TableField(value = "avatar_url")
    @Alias("头像")
    private String avatarUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public User(Integer id, String username, String password, String nickname, String email, String phone, String address, LocalDateTime createTime, String avatarUrl) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.createTime = createTime;
        this.avatarUrl = avatarUrl;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", createTime=" + createTime +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
