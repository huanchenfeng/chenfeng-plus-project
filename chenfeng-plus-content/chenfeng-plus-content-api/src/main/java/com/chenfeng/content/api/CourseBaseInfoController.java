package com.chenfeng.content.api;

import com.chenfeng.content.model.po.CourseBase;
import com.chenfeng.base.model.PageParams;
import com.chenfeng.base.model.PageResult;
import com.chenfeng.content.model.dto.QueryCourseParamsDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @description 课程信息编辑接口
 * @author cfs
 * @date 2023/1/6 11:29
 * @version 1.0
 */
@Api(value="课程信息管理接口",tags = "课程信息管理系统")
@RestController
public class CourseBaseInfoController {
    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody QueryCourseParamsDto queryCourseParams){

        return null;
    }
}