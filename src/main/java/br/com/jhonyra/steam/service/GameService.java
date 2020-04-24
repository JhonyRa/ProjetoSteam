package br.com.jhonyra.steam.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.jhonyra.steam.model.entity.Developer;
import br.com.jhonyra.steam.model.entity.Game;
import br.com.jhonyra.steam.repository.DeveloperRepository;
import br.com.jhonyra.steam.repository.GameRepository;

@Service
public class GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private DeveloperRepository developerRepository;
	
	public List<Game> listAll(){
		
		Game game = new Game();
		game.getDataControl().setDeleted(false);
		
		Example<Game> example = Example.of(game);
		
		return this.gameRepository.findAll(example);
	}
	
	public Game create(Game game){
		
		if(game.getId() != null) {
			throw new ServiceException("Não eh possivel salvar. pois o id esta preenchido.");
		}
		
		if(game.getDeveloper() == null || game.getDeveloper().getId() == null) {
			throw new ServiceException("Objeto developer eh obrigatorio");
		}
		//Validação 2: Verificar se o objeto enviado realmente existe
		Optional<Developer> developer = this.developerRepository.findById(game.getDeveloper().getId());
		
		if(!developer.isPresent()) {
			throw new ServiceException("Nao eh possivel salvar pois o objeto nao existe");
		}
		
		
		game.getDataControl().markCreated(new Date());
		this.gameRepository.save(game);
		
		return game;
	}
	
	public Game update(Game game){
		
		if(game.getId() == null) {
			throw new ServiceException("Não eh possivel salvar. pois o id esta preenchido.");
		}
		
		Optional<Game> currentGame = this.gameRepository.findById(game.getId());
		
		if(!currentGame.isPresent()) {
			throw new ServiceException("Nao eh possivel salvar pois o objeto nao existe");
		}
		
		validDeveloper(game.getDeveloper());
		
		game.setDataControl(currentGame.get().getDataControl());
		
		game.getDataControl().markUpdated(new Date());
		this.gameRepository.save(game);
		
		return game;
	}
	
	public boolean validDeveloper(Developer developer) {
	
		if(developer == null || developer.getId() == null) {
			throw new ServiceException("Objeto developer eh obrigatorio");
		}
	
		Optional<Developer> developerOptional = this.developerRepository.findById(developer.getId());
		
		if(!developerOptional.isPresent()) {
			throw new ServiceException("Objeto informado nao existe");
		}
		
		return true;
	}
	
	/**
	 * @param gameId = id do Game
	 * @return deleta o Game do banco definitivamente.
	 */
	public boolean deletePhysical(Long gameId) {
		
		Optional<Game> currentGame = this.gameRepository.findById(gameId);
		
		if(!currentGame.isPresent()) {
			throw new ServiceException("Não eh possivel deletar pois o objeto nao existe");
		}
		
		this.gameRepository.delete(currentGame.get());
		
		return true;
	}
	
	/**
	 * @param gameId = id do Game
	 * @return "deleta" o game marcando no banco como excluído e preenchendo a data de exclusão, porém mantém o registro.
	 */
	public boolean deleteLogical(Long gameId) {
		
		Optional<Game> currentGame = this.gameRepository.findById(gameId);
		
		if(!currentGame.isPresent()) {
			throw new ServiceException("Não eh possivel deletar pois o objeto nao existe");
		}
		
		currentGame.get().getDataControl().markDeleted(new Date());
		
		this.gameRepository.save(currentGame.get());
		
		return true;
	}

}