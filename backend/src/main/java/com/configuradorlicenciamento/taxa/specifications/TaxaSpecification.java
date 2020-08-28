package com.configuradorlicenciamento.taxa.specifications;

import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.taxa.models.Taxa;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

public class TaxaSpecification {

    private TaxaSpecification() {}

    public static Specification<Taxa> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<Taxa> codigoTaxa(String codigo){

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.join("codigo", JoinType.INNER).get("codigo"))),
                "%" + StringUtil.removeAccents(codigo.toLowerCase()) + "%");
    }

    public static Specification<Taxa> descricao(String codigo){

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.join("codigo", JoinType.INNER).get("descricao"))),
                "%" + StringUtil.removeAccents(codigo.toLowerCase()) + "%");
    }
}
