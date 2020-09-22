package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.specifications;

import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

@AllArgsConstructor
public class TipoCaracterizacaoAtividadeSpecification {

    public static Specification<TipoCaracterizacaoAtividade> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<TipoCaracterizacaoAtividade> filtrarAtividadesDispensaveis() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("dispensaLicenciamento"));
    }

    public static Specification<TipoCaracterizacaoAtividade> atividadeCnaeNome(String nome) {

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.join("atividadeCnae", JoinType.INNER).get("nome"))),
                "%" + StringUtil.removeAccents(nome.toLowerCase()) + "%");

    }

    public static Specification<TipoCaracterizacaoAtividade> atividadeCnaeCodigo(String codigo) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.join("atividadeCnae", JoinType.INNER).get("codigo")), "%" + codigo.toLowerCase() + "%");
    }

}
