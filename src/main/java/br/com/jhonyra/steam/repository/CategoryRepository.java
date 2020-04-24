package br.com.jhonyra.steam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jhonyra.steam.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
