package it.uniroma3.siw.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.spring.model.Opera;

@Repository
public interface OperaRepository extends CrudRepository<Opera, Long> {
	
	public Optional<Opera> findByTitolo(String titolo);
}
