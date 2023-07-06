package com.chenfeng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenfeng.content.model.dto.TeachplanDto;
import com.chenfeng.content.model.po.Teachplan;

import java.util.List;

/**
 * <p>
 * 课程计划 Mapper 接口
 * </p>
 *
 * @author itcast
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {
    /**
     * @description 查询某课程的课程计划，组成树型结构
     * @param courseId
     * @return com.chenfeng.content.model.dto.TeachplanDto
     * @author cfs
     * @date 2022/9/9 11:10
     */
    public List<TeachplanDto> selectTreeNodes(long courseId);
}