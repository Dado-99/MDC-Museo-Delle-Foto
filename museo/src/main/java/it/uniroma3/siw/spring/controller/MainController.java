package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Credenziali;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.CredenzialiService;
import it.uniroma3.siw.spring.service.DipendentiService;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class MainController {

	@Autowired
	private CredenzialiService credenzialiService;

	@Autowired
	private ArtistaService artistaService;

	@Autowired
	private CollezioneService collezioneService;

	@Autowired
	private OperaService operaService;

	@Autowired
	private DipendentiService dipendentiService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private void setModelRole(Model model) {
		boolean isAdmin = false;
		
		try {
			UserDetails dettagliUtente = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credenziali credenziali = credenzialiService.getCredenziali(dettagliUtente.getUsername());
			
			isAdmin = Credenziali.ADMIN_ROLE.equals(credenziali.getRole());
			
		} catch(Exception e) {}
		
		model.addAttribute("isAdmin", isAdmin);
	}

	@RequestMapping(value = "/artista")
	public String getArtisti(Model model) {

		logger.debug("getArtisti");

		model.addAttribute("artisti", artistaService.getAllArtisti());

		setModelRole(model);

		return "artisti";
	}


	@RequestMapping(value="/collezione", method = RequestMethod.GET)
	public String getCollezioni(Model model) {

		logger.debug("getCollezioni");

		model.addAttribute("collezioni", collezioneService.getAllCollezioni());

		setModelRole(model);

		return "collezioni.html";
	}

	@RequestMapping(value = "/opera")
	public String getOpere(Model model) {

		logger.debug("getOpere");

		model.addAttribute("opere", operaService.getAllOpere());

		setModelRole(model);

		return "opere.html";
	}
	
	@RequestMapping(value="/admin/artista/remove/{id}", method=RequestMethod.GET)
	public String removeArtista(@PathVariable("id") Long id, 
								Model model) {
		
		artistaService.removeArtista(id);
		
		return getArtisti(model);
	}
	
	@RequestMapping(value="/admin/opera/remove/{id}", method=RequestMethod.GET)
	public String removeOpera(@PathVariable("id") Long id, 
			Model model) {
		
		operaService.removeOpera(id);
		
		return getOpere(model);
	}
	
	@RequestMapping(value="/admin/collezione/remove/{nome}", method=RequestMethod.GET)
	public String removeCollezione(@PathVariable("nome") String nome, 
								   Model model) {
		
		collezioneService.removeCollezione(nome);
		
		return getCollezioni(model);
	}
	
	@RequestMapping(value="/admin/gestisci", method=RequestMethod.GET)
	public String gestisci(@ModelAttribute("submit") String submit, Model model) {
		
		if("artista".equals(submit)) {
			model.addAttribute("artista", new Artista());
			
			return "admin/artista-form";
		}
		else if("opera".equals(submit)) {
			model.addAttribute("opera", new Opera());
			model.addAttribute("collezioni", collezioneService.getAllCollezioni());
			model.addAttribute("artisti", artistaService.getAllArtisti());
			
			return "admin/opera-form";
		}
		
		
		model.addAttribute("collezione", new Collezione());
		model.addAttribute("dipendenti", dipendentiService.getAllDipendenti());
		
		return "admin/collezione-form";
	}
	
	@RequestMapping(value="/admin/menu", method=RequestMethod.GET)
	public String menu(Model model) {
		return "admin/gestisci";
	}
	
}