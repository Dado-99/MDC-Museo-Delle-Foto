package it.uniroma3.siw.spring.model;

import java.beans.Transient;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"titolo", "autore_id", "dataPubblicazione"}))
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public @Data class Opera {
	
	public static final String operasPath = "images/operas";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String titolo;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataPubblicazione;
	
	private String luogo;
	
	private String immagine;
	
	private Boolean isLong;
	
	@NonNull
	private String descrizione;
	
	@ManyToOne
	private Artista autore;
	
	@ManyToOne
	private Collezione collezione;
	
	@Transient
	public String getPathImmagine() {
		if (immagine == null) return null;
		
		return "/images/operas/" + immagine;
	}
}
