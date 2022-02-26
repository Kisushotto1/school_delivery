package com.shelbourne.schooldelivery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

@Data  //@Data是以下注解的集合：@ToString @EqualsAndHashCode @Getter @Setter @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "username")
    private String username;
    @JsonIgnore  //忽略某个属性不展示给前端
    private String password;
    private String nickname;
    private String email;
    private String phone;

    private String address;
    @TableField(value = "avatar_url")
    private String avatar;
}
