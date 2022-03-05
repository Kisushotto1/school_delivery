package com.shelbourne.schooldelivery.controller.dto;

/*
接收前端登录请求的数据 DTO：data trans object
 */
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String address;
    private String phone;
    private String email;
    private String avatarUrl;
    private String token;

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public UserDTO() {
    }

    //全参构造函数


    public UserDTO(Integer id, String username, String password,
                   String nickname, String address, String phone,
                   String email, String avatarUrl, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.token = token;
    }

    //除密码
    public UserDTO(Integer id, String username, String nickname,
                   String address, String phone, String email,
                   String avatarUrl,String token) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
