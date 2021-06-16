package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.DipendenteService;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class MainController {

	@Autowired
	private ArtistaService artistaService;

	@Autowired
	private CollezioneService collezioneService;

	@Autowired
	private OperaService operaService;

	@Autowired
	private DipendenteService dipendentiService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@RequestMapping(value="/admin/gestisci/artista", method=RequestMethod.GET)
	public String formArtista(Model model) {
		logger.debug("formArtista");
		
		model.addAttribute("artista", new Artista());
		
		return "admin/artista-form";
		
	}
	
	@RequestMapping(value="/admin/gestisci/collezione", method=RequestMethod.GET)
	public String formCollezione(Model model) {
		logger.debug("formCollezione");

		model.addAttribute("collezione", new Collezione());
		model.addAttribute("dipendenti", dipendentiService.getAllDipendenti());
		
		return "admin/collezione-form";
	}
	
	@RequestMapping(value="/admin/gestisci/opera", method=RequestMethod.GET)
	public String formOpera(Model model) {
		logger.debug("formOpera");
		
		model.addAttribute("opera", new Opera());
		model.addAttribute("collezioni", collezioneService.getAllCollezioni());
		model.addAttribute("artisti", artistaService.getAllArtisti());
		
		return "admin/opera-form";
	}
	
	@RequestMapping(value="/admin/gestisci/opere", method=RequestMethod.GET)
	public String getAllOpere(@ModelAttribute("submit") String submit, Model model) {
		logger.debug("getOpere");
		
		model.addAttribute("opere", operaService.getAllOpere());
		
		return "admin/opere";
	}
	
	@RequestMapping(value="/admin/menu", method=RequestMethod.GET)
	public String menu(Model model) {
		logger.debug("menu");
		
		return "admin/gestisci";
	}
	
}