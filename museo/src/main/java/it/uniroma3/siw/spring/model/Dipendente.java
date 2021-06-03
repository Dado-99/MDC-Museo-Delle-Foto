package it.uniroma3.siw.spring.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public @Data class Dipendente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String matricola;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String cognome;

	@Column(nullable = false)
	private LocalDate dataNascita;

	@Column(nullable = false)
	private String luogoNascita;

	@Column(nullable = false)
	private String email;
	
	private String numeroTelefonico;

	@OneToMany(mappedBy = "curatore")
	private List<Collezione> collezioniGestite;
	


}
