package com.api3Dsm.domain.servico;

import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;

import com.api3Dsm.domain.modelo.Parcela;
import com.api3Dsm.domain.repositorio.ParcelaRepositorio;

import net.bytebuddy.asm.Advice.Return;

@Service
public class CalculoStatusAtraso {

    public List<Parcela> gerandoStatus() {
        List<Parcela> parcelasFiltradas = parcelaRepositorio.listaParcelasAtrasadas();
        return parcelasFiltradas;
    }
}
