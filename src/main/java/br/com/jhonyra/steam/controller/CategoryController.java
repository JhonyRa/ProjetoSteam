package br.com.jhonyra.steam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonyra.steam.model.dto.CategoryDto;
import br.com.jhonyra.steam.model.entity.Category;
import br.com.jhonyra.steam.service.CategoryService;
import io.swagger.annotations.ApiOperation;

//conotação para requisições rest.
@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@ApiOperation("Metodo que retorna lista de categorias")
	@GetMapping("/list-all")
	public List<Category> getCategorys(){
		return categoryService.listAll();
	}
	
	@PostMapping("/create")
	public Category createCategory(@RequestBody CategoryDto categoryDto){
		Category category = categoryDto.transfromDtoToCategoryWithoutId();
		return categoryService.create(category);
	}
	
	@PostMapping("/update")
	public Category updateCategory(@RequestBody CategoryDto categoryDto){
		Category category = categoryDto.transfromDtoToCategoryWithId();
		return categoryService.update(category);
	}
		
	@PostMapping("/delete-physical")
	public Boolean deletePhysicalr(@RequestBody Long categoryId){
		
		return this.categoryService.deletePhysical(categoryId);
	}
	
	@PostMapping("/delete-logical")
	public Boolean deleteLogical(@RequestBody Long categoryId){
		
		return this.categoryService.deleteLogical(categoryId);
	}

}
