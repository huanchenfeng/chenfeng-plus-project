package com.chenfeng.content;

import com.chenfeng.content.model.dto.CourseCategoryTreeDto;
import com.chenfeng.content.service.CourseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CourseCategoryServiceTests {
    @Autowired
    CourseCategoryService courseCategoryService;
    @Test
    void testqueryTreeNodes() {
        List<CourseCategoryTreeDto> categoryTreeDtos =
                courseCategoryService.queryTreeNodes("1");
        System.out.println(categoryTreeDtos);
    }
}