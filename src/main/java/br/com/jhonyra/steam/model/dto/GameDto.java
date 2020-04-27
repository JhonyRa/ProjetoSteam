package br.com.jhonyra.steam.model.dto;

import java.util.Date;

import br.com.jhonyra.steam.model.entity.Developer;
import br.com.jhonyra.steam.model.entity.Game;
import lombok.Data;

@Data
public class GameDto {
	
	private Long id;
	private String description;
	private Date release_date;
	private Developer developer;
	
	public Game transfromDtoToGameWithId() {
		
		Game game = new Game();
		game.setId(this.getId());
		game.setDescription(this.getDescription());
		game.setRelease_date(this.getRelease_date());
		game.setDeveloper(this.getDeveloper());
		
		return game;
	}
	
	public Game transfromDtoToGameWithoutId() {
		
		Game game = new Game();
		game.setDescription(this.getDescription());
		game.setRelease_date(this.getRelease_date());
		game.setDeveloper(this.getDeveloper());
		
		return game;
	}

}
