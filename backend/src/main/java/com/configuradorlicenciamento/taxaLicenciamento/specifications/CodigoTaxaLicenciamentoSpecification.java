package com.configuradorlicenciamento.taxaLicenciamento.specifications;

import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.taxaLicenciamento.models.CodigoTaxaLicenciamento;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

public class CodigoTaxaLicenciamentoSpecification {

    private CodigoTaxaLicenciamentoSpecification() {}

    public static Specification<CodigoTaxaLicenciamento> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<CodigoTaxaLicenciamento> codigo(String codigo){

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.get("codigo"))),
                "%" + StringUtil.removeAccents(codigo.toLowerCase()) + "%");

//        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
//                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.join("codigo", JoinType.INNER).get("codigo"))),
//                "%" + StringUtil.removeAccents(codigo.toLowerCase()) + "%");
    }

    public static Specification<CodigoTaxaLicenciamento> descricao(String descricao){

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.get("descricao"))),
                "%" + StringUtil.removeAccents(descricao.toLowerCase()) + "%");

//        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
//                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.join("codigo", JoinType.INNER).get("descricao"))),
//                "%" + StringUtil.removeAccents(descricao.toLowerCase()) + "%");
    }
}
