package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.OperaService;

@Component
public class OperaValidator implements Validator {
	
	@Autowired
	private OperaService operaService;
	
    private static final Logger logger = LoggerFactory.getLogger(OperaValidator.class);
    
	@Override
	public void validate(Object obj, Errors errors) {
		
		Opera o = (Opera)obj;

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori richiesti non nulli");
			
			if (this.operaService.alreadyExists(o)) {
				logger.debug("e' un duplicato");
				
				errors.reject("duplicato");
			}
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Opera.class.equals(clazz);
	}
}
