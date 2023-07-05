package com.chenfeng.content.service.impl;

import com.chenfeng.content.mapper.CourseCategoryMapper;
import com.chenfeng.content.model.dto.CourseCategoryTreeDto;
import com.chenfeng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseCategoryServiceImpl implements
        CourseCategoryService {
    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
        List<CourseCategoryTreeDto> categoryTreeDtos = new ArrayList<>();

        Map<String, CourseCategoryTreeDto> mapTemp = new HashMap<>();
        for (CourseCategoryTreeDto item : courseCategoryTreeDtos) {
            if (!id.equals(item.getId())) {
                mapTemp.put(item.getId(), item);
            }
        }

        for (CourseCategoryTreeDto item : courseCategoryTreeDtos) {
            if (!id.equals(item.getId())) {
                if (item.getParentid().equals(id)) {
                    categoryTreeDtos.add(item);
                }
                CourseCategoryTreeDto parent = mapTemp.get(item.getParentid());
                if (parent != null) {
                    if (parent.getChildrenTreeNodes() == null) {
                        parent.setChildrenTreeNodes(new ArrayList<>());
                    }
                    parent.getChildrenTreeNodes().add(item);
                }
            }
        }

        return categoryTreeDtos;

    }
}