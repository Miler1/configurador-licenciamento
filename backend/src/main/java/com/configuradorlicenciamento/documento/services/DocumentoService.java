package com.configuradorlicenciamento.documento.services;

import com.configuradorlicenciamento.documento.dtos.DocumentoCsv;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.exceptions.ConstraintUniqueViolationException;
import com.configuradorlicenciamento.documento.dtos.DocumentoDTO;
import com.configuradorlicenciamento.documento.interfaces.IDocumentoService;
import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.documento.repositories.DocumentoRepository;
import com.configuradorlicenciamento.tipologia.dtos.TipologiaCsv;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import com.configuradorlicenciamento.usuarioLicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuarioLicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import com.configuradorlicenciamento.documento.specifications.DocumentoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.List;

@Service
public class DocumentoService implements IDocumentoService {

    public static final String DOCUMENTO_EXISTENTE = "Já existe um documento com o mesmo nome.";

    @Autowired
    DocumentoRepository documentoRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public Documento salvar(HttpServletRequest request, DocumentoDTO documentoDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        boolean existsName = documentoRepository.existsByNome(documentoDTO.getNome());

        if (existsName) {
            throw new ConstraintUniqueViolationException(DOCUMENTO_EXISTENTE);
        }

        Documento documento = new Documento.DocumentoBuilder(documentoDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        documentoRepository.save(documento);

        return documento;

    }

    @Override
    public List<DocumentoCsv> listarDocumentoParaCsv() throws Exception {

        List<Documento> documentos = documentoRepository.findAll(Sort.by("nome"));
        List<DocumentoCsv> dtos = new ArrayList<>();

        for (Documento documento : documentos) {
            dtos.add(documento.preparaParaCsv());
        }

        return dtos;
    }

    private Specification<Documento> preparaFiltro(FiltroPesquisa filtro) {

        Specification specification = Specification.where(DocumentoSpecification.padrao());

        if(filtro.getStringPesquisa() != null) {
            specification = specification.and(DocumentoSpecification.nome(filtro.getStringPesquisa()));
        }

        return specification;

    }

    public Page<Documento> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Documento> specification = preparaFiltro(filtro);

        return documentoRepository.findAll(specification, pageable);
    }

    @Override
    public Documento editar (HttpServletRequest request, DocumentoDTO documentoDTO){

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Optional<Documento> documentoSalvo = documentoRepository.findById(documentoDTO.getId())
                .map(documento -> {
                    documento.setNome(documentoDTO.getNome());
                    documento.setPrefixoNomeArquivo(documentoDTO.getPrefixoNomeArquivo());
                    documento.setAtivo(documentoDTO.getAtivo());
                    documento.setCaminhoPasta(documentoDTO.getCaminhoPasta());
                    documento.setDataCadastro(documentoDTO.getDataCadastro());
                    documento.setUsuarioLicenciamento(usuarioLicenciamento);

                    return documento;
                });

        if (documentoRepository.existsByNome(documentoDTO.getNome())){

            Documento documentoExistente = documentoRepository.findByNome(documentoDTO.getNome());

            if (documentoExistente != null && !documentoDTO.getId().equals(documentoExistente.getId())) {

                throw new RuntimeException("Um documento com nome '" + documentoDTO.getNome() + "' já está cadastrado.");
            }

        }
        documentoRepository.save(documentoSalvo.get());

        return documentoSalvo.get();

    }
}
