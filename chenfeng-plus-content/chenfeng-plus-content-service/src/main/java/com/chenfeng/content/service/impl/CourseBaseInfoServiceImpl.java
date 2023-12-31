package com.chenfeng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenfeng.base.execption.chenfengPlusException;
import com.chenfeng.base.model.PageParams;
import com.chenfeng.base.model.PageResult;
import com.chenfeng.content.mapper.CourseBaseMapper;
import com.chenfeng.content.mapper.CourseCategoryMapper;
import com.chenfeng.content.mapper.CourseMarketMapper;
import com.chenfeng.content.model.dto.AddCourseDto;
import com.chenfeng.content.model.dto.CourseBaseInfoDto;
import com.chenfeng.content.model.dto.EditCourseDto;
import com.chenfeng.content.model.dto.QueryCourseParamsDto;
import com.chenfeng.content.model.po.CourseBase;
import com.chenfeng.content.model.po.CourseCategory;
import com.chenfeng.content.model.po.CourseMarket;
import com.chenfeng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {
    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Autowired
    CourseMarketMapper courseMarketMapper;
    @Autowired
    CourseCategoryMapper courseCategoryMapper;
    /**
     * 课程分页查询
     * @param pageParams
     * @param courseParamsDto
     * @return
     */
    @Override
    public PageResult<CourseBase> quserCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto) {
        //拼装查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper=new LambdaQueryWrapper<>();
        //根据名称模糊查询
        queryWrapper.like(StringUtils.isNotBlank(courseParamsDto.getCourseName()),CourseBase::getName,courseParamsDto.getCourseName());
        //根据课件审核状况查询
        queryWrapper.eq(StringUtils.isNotBlank(courseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,courseParamsDto.getAuditStatus());
        //根据课件发布状况查询
        queryWrapper.eq(StringUtils.isNotBlank(courseParamsDto.getPublishStatus()),CourseBase::getStatus,courseParamsDto.getPublishStatus());
        //创建分页
        Page<CourseBase> page=new Page<>(pageParams.getPageNo(),pageParams.getPageSize());
        Page<CourseBase> pageResult=courseBaseMapper.selectPage(page,queryWrapper);
        //数据列表
        List<CourseBase> items=pageResult.getRecords();
        //总记录数
        long total=pageResult.getTotal();
        PageResult<CourseBase> courseBasePageResult=new PageResult<>(items,total,pageParams.getPageNo(),pageParams.getPageSize());
        return courseBasePageResult;
    }
    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId,AddCourseDto dto) {
            //新增对象
            CourseBase courseBaseNew = new CourseBase();
            //将填写的课程信息赋值给新增对象
            BeanUtils.copyProperties(dto,courseBaseNew);
            //设置审核状态
            courseBaseNew.setAuditStatus("202002");
            //设置发布状态
            courseBaseNew.setStatus("203001");
            //机构 id
            courseBaseNew.setCompanyId(companyId);
            //添加时间
            courseBaseNew.setCreateDate(LocalDateTime.now());
            //插入课程基本信息表
            int insert = courseBaseMapper.insert(courseBaseNew);
            if(insert<=0){
                throw new RuntimeException("新增课程基本信息失败");
            }
        CourseMarket courseMarket=new CourseMarket();
        BeanUtils.copyProperties(dto,courseMarket);
        Long id = courseBaseNew.getId();
        courseMarket.setId(id);
        int i = saveCourseMarket(courseMarket);
        if(i<=0){
            throw new RuntimeException("保存课程营销信息失败");
        }
        //查询课程基本信息及营销信息并返回
        return getCourseBaseInfo(id);
    }

    private int saveCourseMarket(CourseMarket courseMarket){
        //收费规则
        String charge = courseMarket.getCharge();
        if(StringUtils.isBlank(charge)){
            throw new RuntimeException("收费规则没有选择");
        }
        //收费规则为收费
        if(charge.equals("201001")){
            if(courseMarket.getPrice() == null || courseMarket.getPrice().floatValue()<=0){
                throw new chenfengPlusException("收费课程价格不能为空");
            }
        }
        //根据 id 从课程营销表查询
        CourseMarket courseMarketObj = courseMarketMapper.selectById(courseMarket.getId());
        if(courseMarketObj == null){
            return courseMarketMapper.insert(courseMarket);
        }else{
            BeanUtils.copyProperties(courseMarket,courseMarketObj);
            courseMarketObj.setId(courseMarket.getId());
            return courseMarketMapper.updateById(courseMarketObj);
        }
    }

    //根据课程 id 查询课程基本信息，包括基本信息和营销信息
    @Override
    public CourseBaseInfoDto getCourseBaseInfo(long courseId){
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase == null){
            return null;
        }
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        if(courseMarket != null){
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }
        //查询分类名称
        CourseCategory courseCategoryBySt =
                courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt =
                courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());
        return courseBaseInfoDto;
}

    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto) {
        //课程 id
        Long courseId = dto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase==null){
            chenfengPlusException.cast("课程不存在");
        }
        //校验本机构只能修改本机构的课程
        if(!courseBase.getCompanyId().equals(companyId)){
            chenfengPlusException.cast("本机构只能修改本机构的课程");
        }
        //封装基本信息的数据
        BeanUtils.copyProperties(dto,courseBase);
        courseBase.setChangeDate(LocalDateTime.now());
        //更新课程基本信息
        int i = courseBaseMapper.updateById(courseBase);
        //封装营销信息的数据
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(dto,courseMarket);
        saveCourseMarket(courseMarket);
        //查询课程信息
        CourseBaseInfoDto courseBaseInfo = this.getCourseBaseInfo(courseId);
        return courseBaseInfo;
    }

}
