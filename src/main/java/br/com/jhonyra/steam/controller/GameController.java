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
	
	@GetMapping("/list-all")
	@ApiOperation("Este eh um metodo para retornar a lista de jogos.")
	public List<Game> getGames(){
		return this.gameService.listAll();
	}
	
	@PostMapping("/create")
	public Game createGame(@RequestBody GameDto gameDto){
		
		Game game = gameDto.transfromDtoToGameWithoutId();
		
		this.gameService.create(game);
		
		return game;
	}
	
	@PostMapping("/update")
	public Game updateGame(@RequestBody GameDto gameDto){
		
		Game game = gameDto.transfromDtoToGameWithId();
		
		this.gameService.update(game);
		
		return game;
	}
	
	@PostMapping("/delete-physical")
	public Boolean deletePhysicalr(@RequestBody Long gameId){
		
		return this.gameService.deletePhysical(gameId);
	}
	
	@PostMapping("/delete-logical")
	public Boolean deleteLogical(@RequestBody Long gameId){
		
		return this.gameService.deleteLogical(gameId);
	}
}