package com.chenfeng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenfeng.base.model.PageParams;
import com.chenfeng.base.model.PageResult;
import com.chenfeng.content.mapper.CourseBaseMapper;
import com.chenfeng.content.model.dto.QueryCourseParamsDto;
import com.chenfeng.content.model.po.CourseBase;
import com.chenfeng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {
    @Autowired
    CourseBaseMapper courseBaseMapper;

    /**
     * 课程分页查询
     * @param pageParams
     * @param courseParamsDto
     * @return
     */
    @Override
    public PageResult<CourseBase> quserCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto) {
        //拼装查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper=new LambdaQueryWrapper<>();
        //根据名称模糊查询
        queryWrapper.like(StringUtils.isNotBlank(courseParamsDto.getCourseName()),CourseBase::getName,courseParamsDto.getCourseName());
        //根据课件审核状况查询
        queryWrapper.eq(StringUtils.isNotBlank(courseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,courseParamsDto.getAuditStatus());
        //根据课件发布状况查询
        queryWrapper.eq(StringUtils.isNotBlank(courseParamsDto.getPublishStatus()),CourseBase::getStatus,courseParamsDto.getPublishStatus());
        //创建分页
        Page<CourseBase> page=new Page<>(pageParams.getPageNo(),pageParams.getPageSize());
        Page<CourseBase> pageResult=courseBaseMapper.selectPage(page,queryWrapper);
        //数据列表
        List<CourseBase> items=pageResult.getRecords();
        //总记录数
        long total=pageResult.getTotal();
        PageResult<CourseBase> courseBasePageResult=new PageResult<>(items,total,pageParams.getPageNo(),pageParams.getPageSize());
        return courseBasePageResult;
    }
}
