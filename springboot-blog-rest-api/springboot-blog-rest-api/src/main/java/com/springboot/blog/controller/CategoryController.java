package com.springboot.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;

@RestController
//@RequestMapping("/api/v1/categories")
public class CategoryController {
	
	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService=categoryService;
	}
	
	@PostMapping("/api/v1/categories")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategories(@RequestBody CategoryDto categoryDto)
	{
		CategoryDto savedCategoryDto= categoryService.addCategory(categoryDto);
		return new ResponseEntity<>(savedCategoryDto,HttpStatus.CREATED);
	}
	
	//build get  Category api
	@GetMapping("/api/v1/categories/{id}")
	public ResponseEntity<CategoryDto> getCategory( @PathVariable("id") Long categoryId)
	{
		
		CategoryDto categoryDto= categoryService.getCategory(categoryId);
		return ResponseEntity.ok(categoryDto);
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories()
	{
		List<CategoryDto> categoryDtos= categoryService.getAllCategories();
		
		return ResponseEntity.ok(categoryDtos);
	}
	
	@PutMapping("/api/v1/categories/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable("id") Long categoryID)
	{
		CategoryDto categoryDto2= categoryService.updateCategory(categoryDto, categoryID);
		return ResponseEntity.ok(categoryDto2);
	}
	
	
	//build delete category api
	@DeleteMapping("/api/v1/categories/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId)
	{
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category Deleted successfully");
	}

	
}
