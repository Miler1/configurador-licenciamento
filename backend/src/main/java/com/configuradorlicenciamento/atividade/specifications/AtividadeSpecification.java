package com.configuradorlicenciamento.atividade.specifications;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

@AllArgsConstructor
public class AtividadeSpecification {

    public static Specification<Atividade> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<Atividade> filtrarAtividadesLicenciaveis() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.notLike(criteriaBuilder.lower(root.get("codigo")), "0000");
    }

    public static Specification<Atividade> filtrarAtividadesDispensaveis() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("codigo")), "0000");
    }

    public static Specification<Atividade> filtrarAtividadesAtuais() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("itemAntigo"));
    }

    public static Specification<Atividade> atividadeNome(String nome) {

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.get("nome"))),
                "%" + StringUtil.removeAccents(nome.toLowerCase()) + "%");

    }

    public static Specification<Atividade> atividadeCodigo(String codigo) {

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("codigo")), "%" + codigo.toLowerCase() + "%");

    }

    public static Specification<Atividade> baseJoin(String coluna1, String coluna2, String referenced, String filter) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.join(coluna1, JoinType.INNER).join(coluna2, JoinType.INNER).get(referenced))),
                "%" + StringUtil.removeAccents(filter.toLowerCase()) + "%");
    }

    public static Specification<Atividade> atividadeCodigoCnae(String codigo) {
        return baseJoin("atividadesCnae", "atividadeCnae", "codigo", codigo);
    }

    public static Specification<Atividade> atividadeNomeCnae(String nome) {
        return baseJoin("atividadesCnae", "atividadeCnae", "nome", nome);
    }

}