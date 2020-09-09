package com.configuradorlicenciamento.requisitoTecnico.specifications;

import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.requisitoTecnico.models.RequisitoTecnico;
import org.springframework.data.jpa.domain.Specification;

public class RequisitoTecnicoSpecification {

    private RequisitoTecnicoSpecification() {}

    public static Specification<RequisitoTecnico> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<RequisitoTecnico> codigo(String codigo) {

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.get("codigo"))),
                "%" + StringUtil.removeAccents(codigo.toLowerCase()) + "%");

    }

    public static Specification<RequisitoTecnico> descricao(String descricao) {

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.get("descricao"))),
                "%" + StringUtil.removeAccents(descricao.toLowerCase()) + "%");

    }

}
