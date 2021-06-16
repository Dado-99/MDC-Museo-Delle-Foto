package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.DipendenteService;

@Controller
public class CollezioneController {
	
	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private CollezioneValidator collezioneValidator;
	
	@Autowired
	private DipendenteService dipendentiService;
	

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/collezione", method = RequestMethod.GET)
	public String getCollezioni(Model model) {

		logger.debug("getCollezioni");

		model.addAttribute("collezioni", collezioneService.getAllCollezioni());

		return "collezioni";
	}
	
	@RequestMapping(value = "/collezione/{nome}", method = RequestMethod.GET )
	public String getCollezionePerId(@PathVariable("nome") String nome,  Model model){
		Collezione c = collezioneService.getCollezione(nome);
		
		model.addAttribute("collezione", c);
		model.addAttribute("opere", c.getOpere());
		
		return "collezione";
	}
	
	@RequestMapping(value="/admin/collezione/save", method=RequestMethod.POST)
	public String saveArtista(@ModelAttribute("collezione") Collezione collezione, 
							  @ModelAttribute("curatore_matricola") String curatore_matricola,
							  BindingResult bindingResult, Model model) {
		
		collezioneValidator.validate(collezione, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			
			collezioneService.saveCollezione(collezione, curatore_matricola);
			
			return "admin/gestisci";
		}
		
		model.addAttribute("collezione", collezione);
		model.addAttribute("dipendenti", dipendentiService.getAllDipendenti());

		return "admin/collezione-form";
	}
	
	@RequestMapping(value="/admin/collezione/remove/{nome}", method=RequestMethod.GET)
	public RedirectView removeCollezione(@PathVariable("nome") String nome, 
								   Model model) {
		
		collezioneService.removeCollezione(nome);
		
		return new RedirectView("/collezione");
	}

}
