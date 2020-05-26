package br.com.jhonyra.steam.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.jhonyra.steam.model.entity.Category;
import br.com.jhonyra.steam.model.entity.Developer;
import br.com.jhonyra.steam.model.entity.Game;
import br.com.jhonyra.steam.repository.CategoryRepository;
import br.com.jhonyra.steam.repository.DeveloperRepository;
import br.com.jhonyra.steam.repository.GameRepository;

@Service
public class GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private DeveloperRepository developerRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Game> listAll(){
		return this.gameRepository.findAll();
	}
	
	public Game create(Game game){
		
		if(game.getId() != null) {
			throw new ServiceException("Nao eh possivel salvar, pois o id esta preeenchido");			
		}
		
		validateDeveloper(game.getDeveloper());
		
		validateCategoryList(game.getCategories());
		
		game.getDataControl().markCreated(new Date());
		this.gameRepository.save(game);
		
		return game;
	}
	
	public Game update(Game game){
		
		if(game.getId() == null) {
			throw new ServiceException("Nao eh possivel salvar, pois o id nao esta preeenchido");			
		}
		
		Optional<Game> currentGame = this.gameRepository.findById(game.getId());
		
		if(!currentGame.isPresent()) {
			throw new ServiceException("Nao eh possivel salvar pois o objeto nao existe");
		}
		
		validateDeveloper(game.getDeveloper());
		
		validateCategoryList(game.getCategories());
		
		game.setDataControl(currentGame.get().getDataControl());
		
		game.getDataControl().markUpdated(new Date());
		this.gameRepository.save(game);
		
		return game;
	}
	
	
	public boolean validateCategoryList(Set<Category> categories) {
		
		if(categories == null) {
			throw new ServiceException("Objeto categories eh obrigatorio");
		}
		
		for(Category category:categories) {
			
			Optional<Category> categoryOptional = this.categoryRepository.findById(category.getId());
			
			if(!categoryOptional.isPresent()) {
				throw new ServiceException("Objeto categoryOptional informando nao existe "+category.getId());
			}
			
			if(categoryOptional.get().getDataControl().getDeleted()) {
				throw new ServiceException("Objeto categoryOptional informando nao ativo "+category.getId());
			}
		}
		
		
		return true;
	}
	
	public boolean validateDeveloper(Developer developer) {
		
		if(developer == null || developer.getId() == null) {
			throw new ServiceException("Objeto developer eh obrigatorio");
		}
		
		Optional<Developer> developerOptional = this.developerRepository.findById(developer.getId());
		
		if(!developerOptional.isPresent()) {
			throw new ServiceException("Objeto developer informando nao existe");
		}
		
		return true;
	}
	
	public boolean delete(Long gameId) {
		
		Optional<Game> currentGame = this.gameRepository.findById(gameId);
		
		if(!currentGame.isPresent()) {
			throw new ServiceException("Nao eh possivel deletar pois o objeto nao existe");
		}
		
		this.gameRepository.delete(currentGame.get());
		
		return true;
	}
	
}