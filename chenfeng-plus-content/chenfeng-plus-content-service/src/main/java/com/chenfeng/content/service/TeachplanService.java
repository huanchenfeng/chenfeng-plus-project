package com.chenfeng.content.service;

import com.chenfeng.content.model.dto.SaveTeachplanDto;
import com.chenfeng.content.model.dto.TeachplanDto;

import java.util.List;

public interface TeachplanService {
    /**
     * @description 查询课程计划树型结构
     * @param courseId 课程 id
     * @return List<TeachplanDto>
     * @author cfs
     * @date 2023/6/9 11:13
     */
    public List<TeachplanDto> findTeachplanTree(long courseId);
    public void saveTeachplan(SaveTeachplanDto teachplanDto);
    public void deleteTeachplan(long id);
    public void teachplanMoveup(long id);
    public void teachableMovedown(long id);
}