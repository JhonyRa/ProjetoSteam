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

//o service eh onde fica as regras/validaçoes.

@Service  // o @Service é usado na classe service e faz anotações nas classes que executam tarefas de serviço

public class GameService { //classe gameService
	
	@Autowired //injeta GameRepository em GameService
	private GameRepository gameRepository; //criando referencia para GameRepository
	
	@Autowired
	private DeveloperRepository developerRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Game> listAll(){ //método public que vai retornar uma lista de Games e possui o alias listAll
		return this.gameRepository.findAll(); //busca todos os games e retorna.
	}
	
	public Game create(Game game){ //método puclic que vai retornar um game e possui o alias create. recebe como parametro game.
		
		if(game.getId() != null) { //verifica se o id do game é difetente de nulo.
			throw new ServiceException("Nao eh possivel salvar, pois o id esta preeenchido"); //exibindo excessão caso rebeu id diferente de nulo.		
		}
		
		validateDeveloper(game.getDeveloper()); //valida a desenvolvedora, passando um parametro/argumento "developer"
		
		validateCategoryList(game.getCategories());//valida a categoria, passando um parameto "categories"
		
		game.getDataControl().markCreated(new Date()); //cria uma data de criação e update, de acordo com regras do método "markCreated" do DataControl
		this.gameRepository.save(game); //salva o game no banco.
		
		return game; //retorna o game q acabou de criar
	}
	
	public Game update(Game game){ //método public que retorna um game que chama update. recebe como parametro game.
		
		if(game.getId() == null) { //verifica se o id do game é igual a null
			throw new ServiceException("Nao eh possivel salvar, pois o id nao esta preenchido"); //se for null exibe exceção	
		}
		
		Optional<Game> currentGame = this.gameRepository.findById(game.getId()); //valida se o id recebido existe no banco.
		
		if(!currentGame.isPresent()) {  //verifica se 
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
		
		for(Category jhonyra:categories) {
			
			Optional<Category> categoryOptional = this.categoryRepository.findById(jhonyra.getId());
			
			if(!categoryOptional.isPresent()) {
				throw new ServiceException("Objeto categoryOptional informando nao existe "+jhonyra.getId());
			}
			
			if(categoryOptional.get().getDataControl().getDeleted()) {
				throw new ServiceException("Objeto categoryOptional informando nao ativo "+jhonyra.getId());
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