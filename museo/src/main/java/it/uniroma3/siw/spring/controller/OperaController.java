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

import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.FileUploadUtil;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class OperaController {
	
	@Autowired
	private OperaService operaService;

	@Autowired
	private OperaValidator operaValidator;
	
	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private ArtistaService artistaService;
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @RequestMapping(value = "/opera")
    public String getOpere(Model model) {
    	
    	logger.debug("getOpere");
    	
    	model.addAttribute("opere", operaService.getAllOpere());
    	
    	return "admin/opere";
    }

    @RequestMapping(value = "/opera/{id}")
	public String getOpera(@PathVariable("id") Long id, Model model) {
		
		logger.debug("getOpera");
		Opera o = operaService.getOpera(id);
		model.addAttribute("opera", o);
		model.addAttribute("autore", o.getAutore());
		
		return "opera.html";
	}
	
	@RequestMapping(value="/admin/opera/save", method=RequestMethod.POST)
	public String saveOpera(@ModelAttribute("opera") Opera opera,
							@ModelAttribute("artista_id") String artista_id,
							@ModelAttribute("collezione_id") String collezione_nome,
							@RequestParam("foto") MultipartFile multipartFile,
							BindingResult bindingResult ,Model model) throws IOException {

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		opera.setImmagine(fileName);
		
		operaValidator.validate(opera, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			
			operaService.saveOpera(opera, Long.parseLong(artista_id), collezione_nome);
			
			FileUploadUtil.saveFile(Opera.operasPath, fileName, multipartFile);
			
			return "admin/gestisci";
		}

		model.addAttribute("opera", opera);
		model.addAttribute("collezioni", collezioneService.getAllCollezioni());
		model.addAttribute("artisti", artistaService.getAllArtisti());
		
		return "admin/opera-form";
	}
	
	@RequestMapping(value="/admin/opera/remove/{id}", method=RequestMethod.GET)
	public String removeOpera(@PathVariable("id") Long id, 
			Model model) {
		
		operaService.removeOpera(id);
		
		return getOpere(model);
	}
	
}
