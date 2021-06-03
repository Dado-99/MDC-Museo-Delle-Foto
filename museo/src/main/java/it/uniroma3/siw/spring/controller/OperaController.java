package it.uniroma3.siw.spring.controller;


import java.io.IOException;
import java.util.NoSuchElementException;

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
import it.uniroma3.siw.spring.service.FileUploadUtil;
import it.uniroma3.siw.spring.service.MvcConfig;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class OperaController {
	
	@Autowired
	private OperaService operaService;

	@Autowired
	private OperaValidator operaValidator;
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/opera/{id}")
	public String getOpera(@PathVariable("id") Long id, Model model) {
		
		logger.debug("getOpera");
		try {
			Opera o = operaService.getOpera(id);
			model.addAttribute("opera", o);
			model.addAttribute("autore", o.getAutore());
			
			return "opera.html";
			
		} catch (NoSuchElementException e)
		{
			return "error.html";
		}
	}
	
	@RequestMapping(value="/admin/opera/save", method=RequestMethod.POST)
	public String saveOpera(@ModelAttribute("opera") Opera opera,
							@ModelAttribute("artista_id") String artista_id,
							@ModelAttribute("collezione_id") String collezione_nome,
							@RequestParam("foto") MultipartFile multipartFile,
							@ModelAttribute("submit") String submit, 
							BindingResult bindingResult ,Model model) throws IOException {
		
		
		if("indietro".equals(submit)) {
			return "admin/gestisci";
		}
		
		operaValidator.validate(opera, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			
			opera.setImmagine(fileName);
			
			operaService.saveOpera(opera, Long.parseLong(artista_id), collezione_nome);
			
			FileUploadUtil.saveFile(MvcConfig.imagesPath, fileName, multipartFile);
			
			return "admin/gestisci";
		}

		model.addAttribute("opera", opera);
		
		return "admin/opera-form";
	}
	
	
}
