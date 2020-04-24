package br.com.jhonyra.steam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonyra.steam.model.entity.Developer;
import br.com.jhonyra.steam.service.DeveloperService;
import io.swagger.annotations.ApiOperation;

//conotação para requisições rest.
@RestController
@RequestMapping("/developer")
public class DeveloperController {
	
	@Autowired
	private DeveloperService developerService;	

	//getmapping aponta para o método de acordo com a url.
	@GetMapping("/list-all")
	@ApiOperation("Este eh um metodo para retornar a lista de desenvolvedoras.")
	public List<Developer> getDevelopers(){
		return this.developerService.listAll();
	}
	
	@PostMapping("/create")
	public Developer createDeveloper(@RequestBody Developer developer){
		
		this.developerService.create(developer);
		
		return developer;
	}
	
	@PostMapping("/update")
	public Developer updateDeveloper(@RequestBody Developer developer){
		
		this.developerService.update(developer);
		
		return developer;
	}
	
	@PostMapping("/delete-physical")
	public Boolean deletePhysicalr(@RequestBody Long developerId){
		
		return this.developerService.deletePhysical(developerId);
	}
	
	@PostMapping("/delete-logical")
	public Boolean deleteLogical(@RequestBody Long developerId){
		
		return this.developerService.deleteLogical(developerId);
	}

}
