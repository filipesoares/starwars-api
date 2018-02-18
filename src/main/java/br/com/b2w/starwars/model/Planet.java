package br.com.b2w.starwars.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Classe de dominio que representa a entidade Planeta
 * @author Filipe Oliveira
 *
 */
@Component
@Document(collection="planets")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Planet implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@NotNull(message="Informe o nome do planeta")
	@Size(min=3, max=30, message="Nome do Planeta com tamanho inválido")
	@Indexed
	private String nome;
	
	@NotNull(message="Informe o clima do planeta")
	@Size(min=3, max=20, message="Clima do Planeta com tamanho inválido")
	private String clima;
	
	@NotNull(message="Informe o terreno do planeta")
	@Size(min=3, max=20, message="Terreno do Planeta com tamanho inválido")
	private String terreno;
	
	@Transient
	private Integer filmes;

	public Planet() {
		super();
	}

	public Planet(String nome, String clima, String terreno) {
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	public Integer getFilmes() {
		return filmes;
	}

	public void setFilmes(Integer filmes) {
		this.filmes = filmes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Planet other = (Planet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
