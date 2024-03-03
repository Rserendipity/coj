package com.cjj.coj.modle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value ="problem")
@Data
public class Problem implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String title;
    private String level;
    private String description;
    private String answer;
    private String tags; // json
    private Integer pass;

    private String judgeConfig; // json
    private String judgeCases; // json

    private Long userId;

    @TableField(select = false)
    private Integer deleted;
}