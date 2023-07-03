package com.chenfeng.content;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenfeng.base.model.PageParams;
import com.chenfeng.base.model.PageResult;
import com.chenfeng.content.mapper.CourseBaseMapper;
import com.chenfeng.content.model.dto.QueryCourseParamsDto;
import com.chenfeng.content.model.po.CourseBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseBaseMapperTests {
    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Test
    public void testCourseBaseMapper(){
        courseBaseMapper.selectById(18);
        Assertions.assertNotNull(courseBaseMapper);

        QueryCourseParamsDto courseParamsDto=new QueryCourseParamsDto();
        courseParamsDto.setCourseName("java");//课程名称查询
        //拼装查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper=new LambdaQueryWrapper<>();
        //根据名称模糊查询
        queryWrapper.like(StringUtils.isNotBlank(courseParamsDto.getCourseName()),CourseBase::getName,courseParamsDto.getCourseName());
        //根据课件审核状况查询
        queryWrapper.eq(StringUtils.isNotBlank(courseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,courseParamsDto.getAuditStatus());
        //分页对象参数
        PageParams pageParams=new PageParams();
        pageParams.setPageNo(1L);
        pageParams.setPageSize(2L);
        //创建分页
        Page<CourseBase> page=new Page<>(pageParams.getPageNo(),pageParams.getPageSize());
        Page<CourseBase> pageResult=courseBaseMapper.selectPage(page,queryWrapper);
        //数据列表
        List<CourseBase> items=pageResult.getRecords();
        //总记录数
        long total=pageResult.getTotal();
        PageResult<CourseBase> courseBasePageResult=new PageResult<CourseBase>(items,total,pageParams.getPageNo(),pageParams.getPageSize());
        System.out.println(courseBasePageResult);
    }
}
