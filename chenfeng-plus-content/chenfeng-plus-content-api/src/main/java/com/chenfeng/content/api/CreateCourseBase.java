package com.chenfeng.content.api;

import com.chenfeng.content.model.dto.AddCourseDto;
import com.chenfeng.content.model.dto.CourseBaseInfoDto;
import com.chenfeng.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value="增添课程信息接口",tags = "课程信息管理系统")
@Slf4j
@RestController
public class CreateCourseBase {
    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("新增课程基础信息")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody AddCourseDto addCourseDto){
        //机构 id，由于认证系统没有上线暂时硬编码
        Long companyId = 1232141425L;
        return courseBaseInfoService.createCourseBase(companyId,addCourseDto);
    }

}
