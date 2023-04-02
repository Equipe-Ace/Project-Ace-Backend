package com.api3Dsm.domain.modelo;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@javax.persistence.Entity
public class Parcela {

	
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column
	private Long idCliente;

	@Column
	private int numeroParcela;
	
	@Column
	private LocalDate dataVencimento;
	
	@Column
	private String dataPagamento;
	
	@Column
	private String dataCredito;
	
	@Column
	private Float valorParcela;
	
	@Column
	private int valorPago;

	public Long getId() {
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public int getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(int numeroParcela) {
		this.numeroParcela = numeroParcela;
	}


	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate  dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getDataCredito() {
		return dataCredito;
	}

	public void setDataCredito(String dataCredito) {
		this.dataCredito = dataCredito;
	}

	public Float getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(Float valorParcela) {
		this.valorParcela = valorParcela;
	}

	public int getValorPago() {
		return valorPago;
	}

	public void setValorPago(int valorPago) {
		this.valorPago = valorPago;
	}
	
	 
}
