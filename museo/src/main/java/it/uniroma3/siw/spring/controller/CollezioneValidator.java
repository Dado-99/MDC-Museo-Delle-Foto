package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.service.CollezioneService;

@Component
public class CollezioneValidator implements Validator {
	
	@Autowired
	private CollezioneService collezioneService;
	
    private static final Logger logger = LoggerFactory.getLogger(CollezioneValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		
		Collezione c = (Collezione) o;
		
		if (!errors.hasErrors()) {
			logger.debug("confermato: valori richiesti non nulli");
			
			c.setNome(c.getNome().trim());
			c.setDescrizione(c.getDescrizione().trim());
			
			if (this.collezioneService.alreadyExists(c)) {
				logger.debug("e' un duplicato");
				
				errors.reject("duplicato");
			}
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Collezione.class.equals(clazz);
	}
}
