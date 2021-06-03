package it.uniroma3.siw.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InformazioniController {

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String getInfo(Model model, HttpSession session) {
		
		return "info.html";
	}
}
