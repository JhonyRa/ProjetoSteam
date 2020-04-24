package br.com.jhonyra.steam.model.dto;

import java.util.Date;

import br.com.jhonyra.steam.model.entity.Developer;
import lombok.Data;

@Data 
public class DeveloperDto {
	
	private Long id;
	private String name;
	private Date foundationDate;
	
	
	public Developer transfromDtoToDeveloperWithId() {
		
		Developer developer = new Developer();
		developer.setId(this.getId());
		developer.setName(this.getName());
		developer.setFoundationDate(this.getFoundationDate());
		
		return developer;
	}
	
	public Developer transfromDtoToDeveloperWithoutId() {
		
		Developer developer = new Developer();
		developer.setName(this.getName());
		developer.setFoundationDate(this.getFoundationDate());
		
		return developer;
	}

}
