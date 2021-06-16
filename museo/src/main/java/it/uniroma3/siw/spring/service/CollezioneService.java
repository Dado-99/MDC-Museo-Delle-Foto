package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Dipendente;
import it.uniroma3.siw.spring.repository.CollezioneRepository;
import it.uniroma3.siw.spring.repository.DipendenteRepository;

@Service
public class CollezioneService {
	
	@Autowired
	private CollezioneRepository collezioneRepository;

	@Autowired
	private DipendenteRepository dipendenteRepository;
	
	@Transactional
	public void saveCollezione(Collezione c, String curatore_matricola) {
		Dipendente d = dipendenteRepository.findById(curatore_matricola).orElse(null);
		
		if(d != null) {
			c.setCuratore(d);
			collezioneRepository.save(c);
		}
	}
	@Transactional
	public void saveCollezione(Collezione c) {
		collezioneRepository.save(c);
	}
	
	@Transactional
	public Collezione getCollezione(String nome) throws NoSuchElementException {
		return collezioneRepository.findById(nome).get();
	}
	
	@Transactional
	public List<Collezione> getAllCollezioni() {
		return (List<Collezione>) collezioneRepository.findAll();
	}

	@Transactional
	public boolean alreadyExists(Collezione c) {
		return collezioneRepository.findById(c.getNome()).orElse(null) != null;
	}
	
	@Transactional
	public void removeCollezione(String nome) {
		collezioneRepository.deleteById(nome);
	}
}
