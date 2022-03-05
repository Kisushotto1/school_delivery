package com.shelbourne.schooldelivery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Shelby
 * @since 2022-03-04
 */
@Data
@ApiModel(value = "File对象", description = "")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("文件名称")
    private String name;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件大小（KB）")
    private Long size;

    @ApiModelProperty("下载链接")
    private String url;

    @ApiModelProperty("是否删除")
    private Boolean deletedTag;

    @ApiModelProperty("链接是否禁用")
    private Boolean enabled;
}

/*
文件完善：
1、size用string，再加一个单位字段，可显示KB和MB两种单位
2、
 */