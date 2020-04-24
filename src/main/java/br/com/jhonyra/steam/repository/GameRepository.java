package br.com.jhonyra.steam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jhonyra.steam.model.entity.Game;

public interface GameRepository extends JpaRepository<Game, Long>{

}
