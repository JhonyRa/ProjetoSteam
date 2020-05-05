package br.com.jhonyra.steam.model.dto;

import java.util.Date;
import java.util.List;

import br.com.jhonyra.steam.model.entity.Game;
import lombok.Data;

@Data
public class GameDto {
	
	private Long id;
	private String name;
	private String description;
	private Date release_date;
	private DeveloperDto developer;
	
	private List<CategoryDto> categories;
	
	public Game transfromDtoToGameWithId() {
		
		Game game = new Game();
		game.setId(this.getId());
		game.setName(this.getName());
		game.setDescription(this.getDescription());
		game.setRelease_date(this.getRelease_date());
		game.setDeveloper(developer.transfromDtoToDeveloperWithId());
		
		//Pega a lista do GameDTO e transforma em um set na entidade GAme
		for(CategoryDto category : categories) {
			game.getCategories().add(category.transfromDtoToCategoryWithId());
		}
		
		return game;
	}
	
	public Game transfromDtoToGameWithoutId() {
		
		Game game = new Game();
		game.setName(this.getName());
		game.setDescription(this.getDescription());
		game.setRelease_date(this.getRelease_date());
		
		for(CategoryDto category : categories) {
			game.getCategories().add(category.transfromDtoToCategoryWithId());
		}
		
		return game;
	}

}
