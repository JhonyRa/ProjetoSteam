package br.com.jhonyra.steam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonyra.steam.model.entity.Category;
import br.com.jhonyra.steam.service.CategoryService;
import io.swagger.annotations.ApiOperation;

//conotação para requisições rest.
@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;	

	//getmapping aponta para o método de acordo com a url.
	@GetMapping("/list-all")
	@ApiOperation("Este eh um metodo para retornar a lista de categorias.")
	public List<Category> getCategories(){
		return this.categoryService.listAll();
	}
	
	@PostMapping("/create")
	public Category createCategory(@RequestBody Category category){
		
		this.categoryService.create(category);
		
		return category;
	}
	
	@PostMapping("/update")
	public Category updateCategory(@RequestBody Category category){
		
		this.categoryService.update(category);
		
		return category;
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
