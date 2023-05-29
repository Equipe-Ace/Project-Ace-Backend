package com.api3Dsm.domain.repositorio;

import java.time.LocalDate;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api3Dsm.domain.modelo.Parcela;


@Repository
public interface ParcelaRepositorio extends JpaRepository<Parcela, Long>{
	

	    @Query("SELECT parcelas FROM Parcela parcelas WHERE parcelas.dataPagamento BETWEEN :dtInicio AND :dtFinal")
	    List<Parcela> dataPagamentoEntre(LocalDate dtInicio, LocalDate dtFinal);

	    @Query("SELECT parcelas FROM Parcela parcelas WHERE parcelas.dataVencimento BETWEEN :dtInicio AND :dtFinal")
	    List<Parcela> dataVencimentoEntre(LocalDate dtInicio, LocalDate dtFinal);

	    @Query("SELECT parcelas FROM Parcela parcelas WHERE parcelas.dataCredito BETWEEN :dtInicio AND :dtFinal")
	    List<Parcela> dataCreditoEntre(LocalDate dtInicio, LocalDate dtFinal);


}
