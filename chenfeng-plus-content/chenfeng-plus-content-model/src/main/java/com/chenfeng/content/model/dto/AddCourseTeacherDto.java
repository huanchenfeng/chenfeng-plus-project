package com.chenfeng.content.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value="AddCourseDto", description="新增教师基本信息")
@Data
public class AddCourseTeacherDto {
    /**
     * 课程标识
     */
    private Long courseId;
    /**
     * 教师标识
     */
    private String teacherName;
    /**
     * 教师职位
     */
    private String position;

    /**
     * 教师简介
     */
    private String introduction;

}
