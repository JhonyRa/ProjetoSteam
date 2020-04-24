package br.com.jhonyra.steam.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.jhonyra.steam.model.entity.Developer;
import br.com.jhonyra.steam.repository.DeveloperRepository;

@Service
public class DeveloperService {
	
	@Autowired
	private DeveloperRepository developerRepository;
	
	public List<Developer> listAll(){
		
		Developer developer = new Developer();
		developer.getDataControl().setDeleted(false);
		
		Example<Developer> example = Example.of(developer);
		
		return this.developerRepository.findAll(example);
	}
	
	public Developer create(Developer developer){
		
		if(developer.getId() != null) {
			throw new ServiceException("Não eh possivel salvar. pois o id esta preenchido.");
		}
		
		developer.getDataControl().markCreated(new Date());
		this.developerRepository.save(developer);
		
		return developer;
	}
	
	public Developer update(Developer developer){
		
		if(developer.getId() == null) {
			throw new ServiceException("Não eh possivel salvar. pois o id esta preenchido.");
		}
		
		Optional<Developer> currentDeveloper = this.developerRepository.findById(developer.getId());
		
		if(!currentDeveloper.isPresent()) {
			throw new ServiceException("Nao eh possivel salvar pois o objeto nao existe");
		}
		
		developer.setDataControl(currentDeveloper.get().getDataControl());
		
		developer.getDataControl().markUpdated(new Date());
		this.developerRepository.save(developer);
		
		return developer;
	}
	
	/**
	 * @param developerId = id da desenvolvedora
	 * @return deleta a desenvolvedora do banco definitivamente.
	 */
	public boolean deletePhysical(Long developerId) {
		
		Optional<Developer> currentDeveloper = this.developerRepository.findById(developerId);
		
		if(!currentDeveloper.isPresent()) {
			throw new ServiceException("Não eh possivel deletar pois o objeto nao existe");
		}
		
		this.developerRepository.delete(currentDeveloper.get());
		
		return true;
	}
	
	/**
	 * @param developerId = id da desenvolvedora
	 * @return "deleta" a desenvolvedora marcando no banco como excluído e preenchendo a data de exclusão, porém mantém o registro.
	 */
	public boolean deleteLogical(Long developerId) {
		
		Optional<Developer> currentDeveloper = this.developerRepository.findById(developerId);
		
		if(!currentDeveloper.isPresent()) {
			throw new ServiceException("Não eh possivel deletar pois o objeto nao existe");
		}
		
		currentDeveloper.get().getDataControl().markDeleted(new Date());
		
		this.developerRepository.save(currentDeveloper.get());
		
		return true;
	}
}