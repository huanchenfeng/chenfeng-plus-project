package com.chenfeng.content.api;

import com.chenfeng.content.model.dto.SaveTeachplanDto;
import com.chenfeng.content.model.dto.TeachplanDto;
import com.chenfeng.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "课程计划编辑接口",tags = "课程计划编辑接口")
@RestController
public class TeachplanController {
    @Autowired
    TeachplanService teachplanService;
    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId",name = "课程 Id",required = true,dataType = "Long",paramType = "path")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId){
        return teachplanService.findTeachplanTree(courseId);
    }
    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan( @RequestBody SaveTeachplanDto teachplan){
        teachplanService.saveTeachplan(teachplan);
    }
    @ApiOperation("课程计划删除")
    @DeleteMapping("/teachplan/{id}")
    public void deleteTeachplan(@PathVariable Long id){
        teachplanService.deleteTeachplan(id);
    }
    @ApiOperation("课程计划向上移动")
    @PostMapping ("/teachplan/moveup/{id}")
    public void teachplanMoveup(@PathVariable Long id){
        teachplanService.teachplanMoveup(id);
    }
    @ApiOperation("课程计划向下移动")
    @PostMapping ("/teachplan/movedown/{id}")
    public void teachableMovedown(@PathVariable Long id){
        teachplanService.teachableMovedown(id);
    }

}