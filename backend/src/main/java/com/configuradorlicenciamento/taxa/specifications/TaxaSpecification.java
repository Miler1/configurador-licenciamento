package com.configuradorlicenciamento.taxa.specifications;

import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.taxa.models.Taxa;
import org.springframework.data.jpa.domain.Specification;

public class TaxaSpecification {

    private TaxaSpecification() {}

    public static Specification<Taxa> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<Taxa> codigo(String codigo) {

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.get("codigo"))),
                "%" + StringUtil.removeAccents(codigo.toLowerCase()) + "%");

    }
}
