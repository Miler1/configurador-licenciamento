package com.configuradorlicenciamento.parametro.specifications;

import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.parametro.models.Parametro;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class ParametroSpecification {

    public static Specification<Parametro> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<Parametro> nome(String nome) {

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.get("nome"))),
                "%" + StringUtil.removeAccents(nome.toLowerCase()) + "%");

    }

    public static Specification<Parametro> codigo(String codigo) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("codigo")), "%" + codigo.toLowerCase() + "%");
    }
}
