package com.cjj.coj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjj.coj.modle.entity.Comments;
import com.cjj.coj.modle.vo.comment.CommentsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comments> {
    @Select("select cm.id, u.account, u.nickname, cm.content, cm.create_time\n" +
            "from comments as cm\n" +
            "         join coj.user u on u.id = cm.user_id\n" +
            "where cm.problem_id = #{problemId}")
    List<CommentsVo> getComment(Long problemId);
}
