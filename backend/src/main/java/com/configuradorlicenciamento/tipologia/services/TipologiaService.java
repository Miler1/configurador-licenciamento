package com.configuradorlicenciamento.tipologia.services;

import br.ufla.lemaf.beans.pessoa.Tipo;
import com.configuradorlicenciamento.atividadeCnae.dtos.AtividadeCnaeCsv;
import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaCsv;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaDTO;
import com.configuradorlicenciamento.tipologia.interfaces.ITipologiaService;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.tipologia.repositories.TipologiaRepository;
import com.configuradorlicenciamento.tipologia.specifications.TipologiaSpecification;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuarioLicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TipologiaService implements ITipologiaService {

    @Autowired
    TipologiaRepository tipologiaRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    public Tipologia salvar(HttpServletRequest request, TipologiaDTO tipologiaDTO) throws Exception{

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Tipologia tipologia = new Tipologia.TipologiaBuilder(tipologiaDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        if(!tipologiaExiste(tipologia)) {
            tipologiaRepository.save(tipologia);
        } else {
            throw new RuntimeException("Uma tipologia ativa já existe com o código informado/gerado");
        }

        return tipologia;

    }

    public Boolean tipologiaExiste(Tipologia tipologia){
        return !tipologiaRepository.findAll(TipologiaSpecification.codigo(tipologia.getCodigo())).isEmpty();
    }

    @Override
    public List<Tipologia> listarTipologia() {
        return tipologiaRepository.findAll(Sort.by("codigo"));
    }

    @Override
    public List<TipologiaCsv> listarTipologiaParaCsv(){

        List<Tipologia> tipologias = listarTipologia();
        List<TipologiaCsv> dtos = new ArrayList<>();

        for (Tipologia tipologia : tipologias) {
            dtos.add(tipologia.preparaParaCsv());
        }

        return dtos;
    }
}
