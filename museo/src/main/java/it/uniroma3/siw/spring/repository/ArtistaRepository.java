package it.uniroma3.siw.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.spring.model.Artista;

@Repository
public interface ArtistaRepository extends CrudRepository<Artista, Long> {
	
	public Optional<Artista> findByNomeAndCognome(String nome, String cognome);
}
