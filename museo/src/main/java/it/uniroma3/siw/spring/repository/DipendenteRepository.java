package it.uniroma3.siw.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Dipendente;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long> {

	public Optional<Dipendente> findByMatricola(String matricola);

}
