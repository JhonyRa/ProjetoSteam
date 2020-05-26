package br.com.jhonyra.steam.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonyra.steam.model.entity.Category;
import br.com.jhonyra.steam.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> listAll(){
		return categoryRepository.findAll();
	}
	
	public Category create(Category category){
		if(category.getId() != null) {
			throw new ServiceException("Não e possivel salvar, pois o id está preenchido");
		}
		category.getDataControl().markCreated(new Date());
		return categoryRepository.save(category);
	}
	
	public Category update(Category category){
		if(category.getId() == null) {
			throw new ServiceException("Não e possivel editar, pois o id não está preenchido");
		}
		Optional<Category> currentCategory = this.categoryRepository.findById(category.getId());
		
		if(!currentCategory.isPresent()) {
			throw new ServiceException("Não e possivel editar, pois o objeto não existe");
		}
		
		category.setDataControl(currentCategory.get().getDataControl());
			
		category.getDataControl().markUpdated(new Date());
		return categoryRepository.save(category);
	}
		
	/**
	 * @param categoryId = id da categoria.
	 * @return deleta a categoria do banco definitivamente.
	 */
	public boolean deletePhysical(Long categoryId) {
		
		Optional<Category> currentCategory = this.categoryRepository.findById(categoryId);
		
		if(!currentCategory.isPresent()) {
			throw new ServiceException("Não eh possivel deletar pois o objeto nao existe");
		}
		
		this.categoryRepository.delete(currentCategory.get());
		
		return true;
	}
	
	/**
	 * @param categoryId = id da categoria.
	 * @return "deleta" a categoria marcando no banco como excluído e preenchendo a data de exclusão, porém mantém o registro.
	 */
	public boolean deleteLogical(Long categoryId) {
		
		Optional<Category> currentCategory = this.categoryRepository.findById(categoryId);
		
		if(!currentCategory.isPresent()) {
			throw new ServiceException("Não eh possivel deletar pois o objeto nao existe");
		}
		
		currentCategory.get().getDataControl().markDeleted(new Date());
		
		this.categoryRepository.save(currentCategory.get());
		
		return true;
	}
}
