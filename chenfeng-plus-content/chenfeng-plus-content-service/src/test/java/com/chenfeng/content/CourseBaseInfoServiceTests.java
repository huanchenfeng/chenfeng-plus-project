package com.chenfeng.content;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenfeng.base.model.PageParams;
import com.chenfeng.base.model.PageResult;
import com.chenfeng.content.mapper.CourseBaseMapper;
import com.chenfeng.content.model.dto.QueryCourseParamsDto;
import com.chenfeng.content.model.po.CourseBase;
import com.chenfeng.content.service.CourseBaseInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseBaseInfoServiceTests {
    @Autowired
    CourseBaseInfoService courseBaseInfoService;
    @Test
    public void testCourseBaseService(){
        QueryCourseParamsDto courseParamsDto=new QueryCourseParamsDto();
        courseParamsDto.setCourseName("java");//课程名称查询
        courseParamsDto.setAuditStatus("202004");
        //分页对象参数
        PageParams pageParams=new PageParams();
        pageParams.setPageNo(1L);
        pageParams.setPageSize(2L);
        PageResult<CourseBase> courseBasePageResult= courseBaseInfoService.quserCourseBaseList(pageParams,courseParamsDto);
        System.out.println(courseBasePageResult);
    }
}
