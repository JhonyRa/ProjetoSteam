package br.com.jhonyra.steam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonyra.steam.model.dto.GameDto;
import br.com.jhonyra.steam.model.entity.Game;
import br.com.jhonyra.steam.service.GameService;
import io.swagger.annotations.ApiOperation;

//conotação para requisições rest.
@RestController
@RequestMapping("/game")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@GetMapping("/list")
	public List<Game> getGames(){
		List<Game> listAll = this.gameService.listAll();
		return listAll;
	}
	
	@PostMapping("/create")
	public Game createGame(@RequestBody GameDto gameDto ){
		
		Game game = gameDto.transformDtoToGameWithoutId();
		
		this.gameService.create(game);
		
		return game;
	}
	
	@PostMapping("/update")
	public Game updateGame(@RequestBody GameDto gameDto ){

		Game game = gameDto.transformDtoToGameWithId();
		
		this.gameService.update(game);
		
		return game;
	}

	@PostMapping("/delete")
	public Boolean deleteGame(@RequestBody Long gameId ){
		return this.gameService.delete(gameId);
	}
	
	
}