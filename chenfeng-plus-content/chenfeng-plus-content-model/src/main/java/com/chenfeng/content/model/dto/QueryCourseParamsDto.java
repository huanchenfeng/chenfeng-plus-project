package com.chenfeng.content.model.dto;

import lombok.Data;
import lombok.ToString;
/**
 * @description 课程查询参数 Dto
 * @author Mr.M
 * @date 2022/9/6 14:36
 * @version 1.0
 */
@Data
@ToString
public class QueryCourseParamsDto {
    //审核状态
    private String auditStatus;
    //课程名称
    private String courseName;
    //发布状态
    private String publishStatus;
}
