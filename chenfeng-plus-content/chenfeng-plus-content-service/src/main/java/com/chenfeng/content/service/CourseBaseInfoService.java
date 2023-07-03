package com.chenfeng.content.service;

import com.chenfeng.base.model.PageParams;
import com.chenfeng.base.model.PageResult;
import com.chenfeng.content.model.dto.QueryCourseParamsDto;
import com.chenfeng.content.model.po.CourseBase;
import org.springframework.stereotype.Service;

//课程信息管理接口

public interface CourseBaseInfoService {
    //课程分页查询
    public PageResult<CourseBase> quserCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);
}

