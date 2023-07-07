package com.chenfeng.content.service;

import com.chenfeng.content.model.po.CourseTeacher;

import java.util.List;

public interface CourseTeacherService {
    public List<CourseTeacher> queryTeachers(Long id);
}
