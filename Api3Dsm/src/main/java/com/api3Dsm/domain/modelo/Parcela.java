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
	private String nomeCliente;

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	@Column
	private int numeroParcela;
	
	@Column
	private LocalDate dataVencimento;
	
	@Column
	private LocalDate dataPagamento;
	
	@Column
	private LocalDate dataCredito;
	
	@Column
	private float valorParcela;
	
	@Column
	private float valorPago;

	@Column
	private String statusVencida;

	public String getStatusVencida() {
		return statusVencida;
	}

	public void setStatusVencida(String statusVencida) {
		this.statusVencida = statusVencida;
	}

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

	public float getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(float precoCadaParcela) {
		this.valorParcela = precoCadaParcela;
	}

	public float getValorPago() {
		return valorPago;
	}

	public void setValorPago(float  valorPago) {
		this.valorPago = valorPago;
	}

}