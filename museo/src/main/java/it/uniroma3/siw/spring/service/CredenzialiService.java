package it.uniroma3.siw.spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Credenziali;
import it.uniroma3.siw.spring.repository.CredenzialiRepository;

@Service
public class CredenzialiService {

//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CredenzialiRepository credenzialiRepository;
	
	public Credenziali getCredenziali(String username) {
		Optional<Credenziali> result = this.credenzialiRepository.findByUsername(username);
		return result.orElse(null);
	}

}
