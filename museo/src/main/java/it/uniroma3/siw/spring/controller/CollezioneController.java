package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.service.CollezioneService;

@Controller
public class CollezioneController {
	
	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private CollezioneValidator collezioneValidator;
	
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
							  @ModelAttribute("submit") String submit, 
							  BindingResult bindingResult, Model model) {
		
		if("indietro".equals(submit)) {
			return "admin/gestisci";
		}
		
		collezioneValidator.validate(collezione, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			
			collezioneService.saveCollezione(collezione, curatore_matricola);
			
			return "admin/gestisci";
		}
		
		model.addAttribute("collezione", collezione);
		return "admin/collezione-form";
	}

}
