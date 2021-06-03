package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.repository.ArtistaRepository;

@Service
public class ArtistaService {
	
	@Autowired
	private ArtistaRepository artistaRepository;
	
	@Transactional
	public void saveArtista(Artista a) {
		artistaRepository.save(a);
	}

	@Transactional
	public void removeArtista(Long id) {
		artistaRepository.deleteById(id);
	}
	
	@Transactional
	public Artista getArtista(Long id) throws NoSuchElementException {
		return artistaRepository.findById(id).get();
	}
	
	@Transactional
	public List<Artista> getAllArtisti() {
		return (List<Artista>) artistaRepository.findAll();
	}

	@Transactional
	public boolean alreadyExists(Artista a) {
		return artistaRepository.findByNomeAndCognome(a.getNome(), a.getCognome()).orElse(null) != null;
	}
}
