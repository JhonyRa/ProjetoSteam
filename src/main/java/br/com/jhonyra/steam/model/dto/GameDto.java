package br.com.jhonyra.steam.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.jhonyra.steam.model.entity.Game;
import lombok.Data;

@Data
public class GameDto {
	
	private Long id;
	private String name;
	private String description;
	private Date releaseDate;
	private DeveloperDto developer;
	private List<CategoryDto> categories;
	
	public List<CategoryDto> getCategories(){
		if(this.categories == null) {
			this.categories = new ArrayList<>();
		}
		
		return this.categories;
	}

	public Game transformDtoToGameWithId() {
		
		Game game = new Game();
		game.setId(this.getId());
		game.setName(this.getName());
		game.setDescription(this.getDescription());
		game.setRelease_date(this.getReleaseDate());
		
		if(this.getDeveloper() != null) {
			game.setDeveloper(this.getDeveloper().transfromDtoToDeveloperWithId());
		}
		
		return game;
	}

	public Game transformDtoToGameWithoutId() {
		
		Game game = new Game();
		game.setName(this.getName());
		game.setDescription(this.getDescription());
		game.setRelease_date(this.getReleaseDate());
		
		if(this.getDeveloper() != null) {
			game.setDeveloper(this.getDeveloper().transfromDtoToDeveloperWithId());
		}
		
		for(CategoryDto categoryDto : this.getCategories()){
			game.getCategories().add(categoryDto.transfromDtoToCategoryWithId());
		}
		
		return game;
	}
}