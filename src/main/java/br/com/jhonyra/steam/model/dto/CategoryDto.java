package br.com.jhonyra.steam.model.dto;

import br.com.jhonyra.steam.model.entity.Category;
import lombok.Data;

@Data
public class CategoryDto {

	private Long id;
	private String name;
	
public Category transfromDtoToCategoryWithId() {
		
		Category category = new Category();
		category.setId(this.getId());
		category.setName(this.getName());
			
		return category;
	}
	
	public Category transfromDtoToCategoryWithoutId() {
		
		Category category = new Category();
		category.setName(this.getName());
		
		return category;
	}
}
