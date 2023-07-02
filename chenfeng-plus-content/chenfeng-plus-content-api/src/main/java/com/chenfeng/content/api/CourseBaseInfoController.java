package com.chenfeng.content.api;

import com.chenfeng.content.model.po.CourseBase;
import com.chenfeng.base.model.PageParams;
import com.chenfeng.base.model.PageResult;
import com.chenfeng.content.model.dto.QueryCourseParamsDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @description 课程信息编辑接口
 * @author Mr.M
 * @date 2022/9/6 11:29
 * @version 1.0
 */
@RestController
public class CourseBaseInfoController {
    @RequestMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody QueryCourseParamsDto queryCourseParams){
        return null;
    }
}