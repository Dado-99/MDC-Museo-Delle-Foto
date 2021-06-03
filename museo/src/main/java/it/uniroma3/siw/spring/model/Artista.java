package it.uniroma3.siw.spring.model;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public @Data class Artista{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String cognome;

	@Column(nullable = false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataNascita;

	@Column(nullable = false)
	private String luogoNascita;
	
	@Column(nullable = false)
	private String nazionalita;

	private LocalDate dataMorte;

	private String immagine;
	
	private String luogoMorte;
	
	private String descrizione;
	
	
	private String unspalsLink;
	
	@OneToMany(mappedBy = "autore", cascade = CascadeType.ALL)
	private List<Opera> opere;
	
	@Transient
	public String getPathImmagine() {
		if (immagine == null) return null;
		
		return "/images/artists/" + immagine;
	}
}
