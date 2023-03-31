package com.api3Dsm.domain.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@javax.persistence.Entity
public class Servico {
	

	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column
	private Float Preco;
	
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Parcela> parcelas = new HashSet<>();
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getPreco() {
		return Preco;
	}

	public void setPreco(Float preco) {
		Preco = preco;
	}

	public Set<Parcela> getParcelas() {
		return parcelas;
	}

	public void setParcelas(Set<Parcela> parcelas) {
		this.parcelas = parcelas;
	}

}
