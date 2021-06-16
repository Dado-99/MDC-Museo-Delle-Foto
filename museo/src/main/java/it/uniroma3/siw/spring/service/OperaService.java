package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.repository.ArtistaRepository;
import it.uniroma3.siw.spring.repository.CollezioneRepository;
import it.uniroma3.siw.spring.repository.OperaRepository;

@Service
public class OperaService {
	
	@Autowired
	private OperaRepository operaRepository;
	
	@Autowired
	private ArtistaRepository artistaRepository;
	
	@Autowired
	private CollezioneRepository collezioneRepository;
	
	@Transactional
	public void saveOpera(Opera o) {
		operaRepository.save(o);
	}
	@Transactional
	public void saveOpera(Opera o, Long artista_id, String collezione_nome) {
		Artista a = artistaRepository.findById(artista_id).orElse(null);
		
		Collezione c = collezioneRepository.findById(collezione_nome).orElse(null);
		
		if(a == null || c == null) {
			return;
		}
		
		o.setAutore(a);
		o.setCollezione(c);
		
		operaRepository.save(o);
	}
	
	@Transactional
	public Opera getOpera(Long id) throws NoSuchElementException {
		return operaRepository.findById(id).get();
	}
	
	@Transactional
	public List<Opera> getAllOpere() {
		return (List<Opera>) operaRepository.findAll();
	}

	@Transactional
	public boolean alreadyExists(Opera o) {
		return operaRepository.findByTitolo(o.getTitolo()).orElse(null) != null;
	}
	
	@Transactional
	public void removeOpera(Long id) {
		operaRepository.deleteById(id);
	}
	
}
