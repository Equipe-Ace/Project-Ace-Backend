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
	private LocalDate	dataPagamento;
	
	@Column
	private LocalDate	dataCredito;
	
	@Column
	private double valorParcela;
	
	@Column
	private Double valorPago;

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

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public LocalDate getDataCredito() {
		return dataCredito;
	}

	public void setDataCredito(LocalDate dataCredito) {
		this.dataCredito = dataCredito;
	}

	public double getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(double precoCadaParcela) {
		this.valorParcela = precoCadaParcela;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double  valorPago) {
		this.valorPago = valorPago;
	}

	public void setValorParcela(Double precoCadaParcela) {
		// TODO Auto-generated method stub
		
	}
	
	 
}
