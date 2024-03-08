package com.cjj.coj.modle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName submission
 */
@TableName(value ="submission")
@Data
public class Submit implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long problemId;

    private Long userId;

    private String language;

    private String code;

    private Integer state;

    private String judgeInfo;

    private Date time;

    private static final long serialVersionUID = 1L;
}