package com.chenfeng.content.api;

import com.chenfeng.content.model.dto.CourseBaseInfoDto;
import com.chenfeng.content.model.po.CourseBase;
import com.chenfeng.base.model.PageParams;
import com.chenfeng.base.model.PageResult;
import com.chenfeng.content.model.dto.QueryCourseParamsDto;
import com.chenfeng.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description 课程信息编辑接口
 * @author cfs
 * @date 2023/1/6 11:29
 * @version 1.0
 */
@Api(value="课程信息管理接口",tags = "课程信息管理系统")
@RestController
public class CourseBaseInfoController {
    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody QueryCourseParamsDto queryCourseParams){
        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.quserCourseBaseList(pageParams, queryCourseParams);
        return courseBasePageResult;
    }

    @ApiOperation("根据课程 id 查询课程基础信息")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId){
        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }
}