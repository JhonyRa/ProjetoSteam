package br.com.jhonyra.steam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jhonyra.steam.model.entity.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, Long>{

}
