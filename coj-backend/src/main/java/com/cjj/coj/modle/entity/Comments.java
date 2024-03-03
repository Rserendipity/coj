package com.cjj.coj.modle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName comments
 */
@TableName(value ="comments")
@Data
public class Comments implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private Long problemId;

    private String content;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}