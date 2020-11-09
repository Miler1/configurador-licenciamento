package com.configuradorlicenciamento.atividade.specifications;

import com.configuradorlicenciamento.atividade.models.Atividade;
import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

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

}