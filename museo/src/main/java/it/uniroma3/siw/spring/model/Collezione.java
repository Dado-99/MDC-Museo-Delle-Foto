package it.uniroma3.siw.spring.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public @Data class Collezione {
	@Id
	private String nome;

	private String descrizione;

	@ManyToOne
	private Dipendente curatore;
	
	@OneToMany(mappedBy = "collezione", cascade = CascadeType.ALL)
	private List<Opera> opere; 
}
