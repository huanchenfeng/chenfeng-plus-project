package com.chenfeng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenfeng.content.mapper.CourseTeacherMapper;
import com.chenfeng.content.model.po.CourseTeacher;
import com.chenfeng.content.service.CourseCategoryService;
import com.chenfeng.content.service.CourseTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {
    @Autowired
    CourseTeacherMapper courseTeacherMapper;
    @Override
    public List<CourseTeacher> queryTeachers(Long id) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("course_id",id);
        return courseTeacherMapper.selectList(queryWrapper);
    }
}
