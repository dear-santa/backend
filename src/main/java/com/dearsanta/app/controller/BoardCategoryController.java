package com.dearsanta.app.controller;

import com.dearsanta.app.service.BoardCategoryService;
import com.dearsanta.app.dto.MainCategoriesDto;
import com.dearsanta.app.dto.SubCategoriesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/categories")
@RestController
public class BoardCategoryController {

    @Autowired
    private BoardCategoryService boardCategoryService;

    @GetMapping("/main-categories")
    public ResponseEntity<MainCategoriesDto> getMainCategories() {
        MainCategoriesDto mainCategories = boardCategoryService.getMainCategories();
        return ResponseEntity.status(HttpStatus.OK).body(mainCategories);
    }

    @GetMapping("/main-categories/{mainCategory}/sub-categories")
    public ResponseEntity<SubCategoriesDto> getSubCategories(
            @PathVariable("mainCategory") String mainCategory
    ) {
        SubCategoriesDto subCategories = boardCategoryService.getSubCategories(mainCategory);
        return ResponseEntity.status(HttpStatus.OK).body(subCategories);
    }
}