package com.configuradorlicenciamento.atividadeCnae.specifications;

import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class AtividadeCnaeSpecification {

    public static Specification<AtividadeCnae> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<AtividadeCnae> nome(String nome) {

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.get("nome"))),
                "%" + StringUtil.removeAccents(nome.toLowerCase()) + "%");

    }

    public static Specification<AtividadeCnae> codigo(String codigo) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("codigo")), "%" + codigo.toLowerCase() + "%");
    }

}
