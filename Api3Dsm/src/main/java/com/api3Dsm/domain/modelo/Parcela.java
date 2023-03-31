package com.api3Dsm.domain.modelo;


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
	private String dataVencimento;
	
	@Column
	private String dataPagamento;
	
	@Column
	private String dataCredito;
	
	@Column
	private Float valorParcela;
	
	@Column
	private Float valorPago;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String  dataVencimento) {
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

	public Float getValorPago() {
		return valorPago;
	}

	public void setValorPago(Float valorPago) {
		this.valorPago = valorPago;
	}
	
	 
}
