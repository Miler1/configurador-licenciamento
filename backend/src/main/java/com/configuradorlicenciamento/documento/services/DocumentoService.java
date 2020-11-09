package com.configuradorlicenciamento.documento.services;

import com.configuradorlicenciamento.configuracao.exceptions.ConflictException;
import com.configuradorlicenciamento.configuracao.utils.FiltroPesquisa;
import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.documento.dtos.DocumentoCsv;
import com.configuradorlicenciamento.documento.dtos.DocumentoDTO;
import com.configuradorlicenciamento.documento.interfaces.IDocumentoService;
import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.documento.repositories.DocumentoRepository;
import com.configuradorlicenciamento.documento.specifications.DocumentoSpecification;
import com.configuradorlicenciamento.requisitoAdministrativo.models.RequisitoAdministrativo;
import com.configuradorlicenciamento.requisitoAdministrativo.repositories.RequisitoAdministrativoRepository;
import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import com.configuradorlicenciamento.requisitoTecnico.models.TipoLicencaGrupoDocumento;
import com.configuradorlicenciamento.requisitoTecnico.repositories.RequisitoTecnicoRepository;
import com.configuradorlicenciamento.requisitoTecnico.repositories.TipoLicencaGrupoDocumentoRepository;
import com.configuradorlicenciamento.usuariolicenciamento.models.UsuarioLicenciamento;
import com.configuradorlicenciamento.usuariolicenciamento.repositories.UsuarioLicenciamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService implements IDocumentoService {

    public static final String DOCUMENTO_EXISTENTE = "Já existe um documento com o mesmo nome.";

    private static final String VINCULO_EXISTENTE = "Não é possível inativar o registro pois, ele se encontra vinculado a uma atividade ativa no sistema.";

    @Autowired
    DocumentoRepository documentoRepository;

    @Autowired
    TipoLicencaGrupoDocumentoRepository tipoLicencaGrupoDocumentoRepository;

    @Autowired
    RequisitoTecnicoRepository requisitoTecnicoRepository;

    @Autowired
    RequisitoAdministrativoRepository requisitoAdministrativoRepository;

    @Autowired
    UsuarioLicenciamentoRepository usuarioLicenciamentoRepository;

    @Override
    public Documento salvar(HttpServletRequest request, DocumentoDTO documentoDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        documentoDTO.setNome(StringUtil.tratarEspacos(documentoDTO.getNome()));

        boolean nomeExistente = documentoRepository.existsByNome(documentoDTO.getNome());

        if (nomeExistente) {
            throw new ConflictException(DOCUMENTO_EXISTENTE);
        }

        Documento documento = new Documento.DocumentoBuilder(documentoDTO)
                .setDataCadastro(new Date())
                .setUsuarioLicencimento(usuarioLicenciamento)
                .build();

        documentoRepository.save(documento);

        return documento;

    }

    @Override
    public List<DocumentoCsv> listarDocumentoParaCsv() {

        List<Documento> documentos = documentoRepository.findAll(Sort.by("nome"));
        List<DocumentoCsv> dtos = new ArrayList<>();

        for (Documento documento : documentos) {
            dtos.add(documento.preparaParaCsv());
        }

        return dtos;

    }

    private Specification<Documento> preparaFiltro(FiltroPesquisa filtro) {

        Specification<Documento> specification = Specification.where(DocumentoSpecification.padrao());

        if (filtro.getStringPesquisa() != null) {
            specification = specification.and(DocumentoSpecification.nome(filtro.getStringPesquisa())
                    .or(DocumentoSpecification.prefixoNomeArquivo(filtro.getStringPesquisa())));
        }

        return specification;

    }

    public Page<Documento> listar(Pageable pageable, FiltroPesquisa filtro) {

        Specification<Documento> specification = preparaFiltro(filtro);

        return documentoRepository.findAll(specification, pageable);

    }

    @Override
    public List<Documento> findAll() {

        return documentoRepository.findAll();

    }

    @Override
    public List<Documento> findByAtivos() {
        return documentoRepository.findByAtivo(true);
    }


    @Override
    public Documento editar(HttpServletRequest request, DocumentoDTO documentoDTO) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        documentoDTO.setNome(StringUtil.tratarEspacos(documentoDTO.getNome()));

        boolean nomeExistente = documentoRepository.existsByNome(documentoDTO.getNome());

        if (nomeExistente) {

            Documento documentoExistente = documentoRepository.findByNome(documentoDTO.getNome());

            if (documentoExistente != null && !documentoDTO.getId().equals(documentoExistente.getId())) {

                throw new ConflictException(DOCUMENTO_EXISTENTE);
            }

        }

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

        documentoRepository.save(documentoSalvo.get());

        return documentoSalvo.get();

    }

    @Override
    public Documento ativarDesativar(HttpServletRequest request, Integer idDocumento) {

        Object login = request.getSession().getAttribute("login");

        UsuarioLicenciamento usuarioLicenciamento = usuarioLicenciamentoRepository.findByLogin(login.toString());

        Documento documentoExistente = documentoRepository.findById(idDocumento).get();

        List<TipoLicencaGrupoDocumento> tipoLicencaGrupoDocumentoList = tipoLicencaGrupoDocumentoRepository.findByDocumento(documentoExistente);

        tipoLicencaGrupoDocumentoList.forEach(tipoLicencaGrupoDocumento -> {

            RequisitoTecnico requisitoTecnico = requisitoTecnicoRepository.findById(tipoLicencaGrupoDocumento.getRequisitoTecnico().getId()).get();

            boolean ativo = requisitoTecnico.getAtivo();

            if (ativo) {
                throw new ConflictException(VINCULO_EXISTENTE);
            }

        });

        List<RequisitoAdministrativo> requisitoAdministrativoList = requisitoAdministrativoRepository.findByDocumento(documentoExistente);

        requisitoAdministrativoList.forEach(requisitoAdministrativo -> {

            boolean ativo = requisitoAdministrativo.getAtivo();

            if (ativo) {
                throw new ConflictException(VINCULO_EXISTENTE);
            }

        });

        Optional<Documento> documentoSalvo = documentoRepository.findById(idDocumento)
                .map(documento -> {
                    documento.setAtivo(!documentoExistente.getAtivo());
                    documento.setDataCadastro(new Date());
                    documento.setUsuarioLicenciamento(usuarioLicenciamento);
                    return documento;
                });

        documentoRepository.save(documentoSalvo.get());

        return documentoSalvo.get();

    }
}
