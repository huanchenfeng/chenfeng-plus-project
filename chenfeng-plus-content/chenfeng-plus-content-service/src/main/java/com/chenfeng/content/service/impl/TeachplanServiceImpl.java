package com.chenfeng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenfeng.base.execption.chenfengPlusException;
import com.chenfeng.content.mapper.TeachplanMapper;
import com.chenfeng.content.mapper.TeachplanMediaMapper;
import com.chenfeng.content.model.dto.SaveTeachplanDto;
import com.chenfeng.content.model.dto.TeachplanDto;
import com.chenfeng.content.model.po.Teachplan;
import com.chenfeng.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeachplanServiceImpl implements TeachplanService {
    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    TeachplanMediaMapper teachplanMediaMapper;
    @Override
    public List<TeachplanDto> findTeachplanTree(long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Transactional
    @Override
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {
        //课程计划 id
        Long id = teachplanDto.getId();
        //修改课程计划
        if(id!=null){
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(teachplanDto,teachplan);
            teachplanMapper.updateById(teachplan);
        }else{
            //取出同父同级别的课程计划数量
            int count = getTeachplanCount(teachplanDto.getCourseId(),
                    teachplanDto.getParentid());
            Teachplan teachplanNew = new Teachplan();
            //设置排序号
            teachplanNew.setOrderby(count+1);
            BeanUtils.copyProperties(teachplanDto,teachplanNew);
            teachplanMapper.insert(teachplanNew);
        }
    }

    @Override
    public void deleteTeachplan(long id) {
        if(teachplanMapper.selectById(id).getParentid()==0) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("parentid", id);
            if (teachplanMapper.selectList(queryWrapper).size() != 0) {
                System.out.println(teachplanMapper.selectList(queryWrapper).toString());
                chenfengPlusException.cast("课程计划信息还有子级信息，无法操作");
            }
            teachplanMapper.deleteById(id);
        }
        else {
            teachplanMapper.deleteById(id);
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("teachplan_id",id);
            teachplanMediaMapper.delete(queryWrapper);
        }
    }

    @Override
    public void teachplanMoveup(long id) {
        // 获取要移动的记录
        Teachplan sourceRecord = teachplanMapper.selectById(id);
        QueryWrapper<Teachplan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",sourceRecord.getCourseId());
        queryWrapper.eq("grade",sourceRecord.getGrade());
        queryWrapper.eq("parentid",sourceRecord.getParentid());
        if(sourceRecord.getOrderby()-1>0){
            queryWrapper.eq("orderby",sourceRecord.getOrderby()-1);
            Teachplan targetRecord = teachplanMapper.selectOne(queryWrapper);
            // 获取目标位置的记录
            Integer sourceOrderby = sourceRecord.getOrderby();
            Integer targetOrderby = targetRecord.getOrderby();
            // 交换orderby值
            sourceRecord.setOrderby(targetOrderby);
            targetRecord.setOrderby(sourceOrderby);
            // 更新要移动的记录和目标位置的orderby值
            teachplanMapper.updateById(sourceRecord);
            teachplanMapper.updateById(targetRecord);
        }
    }

    @Override
    public void teachableMovedown(long id) {
        // 获取要移动的记录
        Teachplan sourceRecord = teachplanMapper.selectById(id);
        QueryWrapper<Teachplan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",sourceRecord.getCourseId());
        queryWrapper.eq("grade",sourceRecord.getGrade());
        queryWrapper.eq("parentid",sourceRecord.getParentid());
        queryWrapper.eq("orderby",sourceRecord.getOrderby()+1);
        if(teachplanMapper.selectOne(queryWrapper)!=null){
            Teachplan targetRecord = teachplanMapper.selectOne(queryWrapper);
            // 获取目标位置的记录
            Integer sourceOrderby = sourceRecord.getOrderby();
            Integer targetOrderby = targetRecord.getOrderby();
            // 交换orderby值
            sourceRecord.setOrderby(targetOrderby);
            targetRecord.setOrderby(sourceOrderby);
            // 更新要移动的记录和目标位置的orderby值
            teachplanMapper.updateById(sourceRecord);
            teachplanMapper.updateById(targetRecord);
        }
    }

    /**
     * @description 获取最新的排序号
     * @param courseId 课程 id
     * @param parentId 父课程计划 id
     * @return int 最新排序号
     * @author cfs
     * @date 2022/7/6 13:43
     */
    private int getTeachplanCount(long courseId,long parentId){
        LambdaQueryWrapper<Teachplan> queryWrapper = new
                LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,courseId);
        queryWrapper.eq(Teachplan::getParentid,parentId);
        Integer count = teachplanMapper.selectCount(queryWrapper);
        return count;
    }
}