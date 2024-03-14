package com.cjj.coj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjj.coj.modle.entity.Submit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SubmitMapper extends BaseMapper<Submit> {
    @Select("select * from submission\n" +
            "where user_id = #{userId} \n" +
            "  and problem_id = #{problemId} \n" +
            "  and (state = 0 or state = 1) limit 1;")
    Submit selectExistPendingOrJudging(@Param("userId") Long userId, @Param("problemId") Long problemId);
}
