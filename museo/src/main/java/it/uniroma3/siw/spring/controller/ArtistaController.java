package it.uniroma3.siw.spring.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.FileUploadUtil;

@Controller
public class ArtistaController {
	
	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private ArtistaValidator artistaValidator;
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@RequestMapping(value = "/artista")
	public String getArtisti(Model model) {

		logger.debug("getArtisti");

		model.addAttribute("artisti", artistaService.getAllArtisti());

		return "artisti";
	}
    
	@RequestMapping(value = "/artista/{id}", method = RequestMethod.GET)
	public String getArtista(@PathVariable("id") Long id, Model model) {
		
		logger.debug("getArtista");
		
		Artista a = artistaService.getArtista(id);
		model.addAttribute("artista", a);
		model.addAttribute("opere", a.getOpere());
		
		return "artista";
	}
	
	@RequestMapping(value="/admin/artista/save", method=RequestMethod.POST)
	public String saveArtista(@ModelAttribute("artista") Artista artista,
							  @RequestParam("foto") MultipartFile multipartFile,
							  String submit, BindingResult bindingResult,
							  Model model) throws IOException {
		
		if("indietro".equals(submit)) {
			return "admin/gestisci";
		}

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		artista.setImmagine(fileName);
		
		artistaValidator.validate(artista, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			
			artistaService.saveArtista(artista);
			
			FileUploadUtil.saveFile(Artista.artistsPath, fileName, multipartFile);
			
			return "admin/gestisci";
		}

		model.addAttribute("artista", artista);
		return "admin/artista-form";
	}

	@RequestMapping(value="/admin/artista/remove/{id}", method=RequestMethod.GET)
	public String removeArtista(@PathVariable("id") Long id, 
								Model model) {
		
		artistaService.removeArtista(id);
		
		return getArtisti(model);
	}
	
}
