package com.chenfeng.content.api;

import com.chenfeng.content.model.dto.CourseCategoryTreeDto;
import com.chenfeng.content.model.po.CourseTeacher;
import com.chenfeng.content.service.CourseTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CourseTeacherController {
    @Autowired
    CourseTeacherService courseTeacherService;
    @GetMapping("/courseTeacher/list/{id}")
    public List<CourseTeacher> queryTeachers(Long id) {
        return courseTeacherService.queryTeachers(id);
    }
    @PostMapping("/courseTeacher")
    public CourseTeacher addTeacher(Long id) {
        return null;
    }
}
