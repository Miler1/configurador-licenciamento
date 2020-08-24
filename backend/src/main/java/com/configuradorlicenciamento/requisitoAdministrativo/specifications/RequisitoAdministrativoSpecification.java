package com.configuradorlicenciamento.requisitoAdministrativo.specifications;

import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.requisitoAdministrativo.models.RequisitoAdministrativo;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

@AllArgsConstructor
public class RequisitoAdministrativoSpecification {

    public static Specification<RequisitoAdministrativo> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<RequisitoAdministrativo> documento(String documento) {

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.join("documento", JoinType.INNER).get("nome"))),
                "%" + StringUtil.removeAccents(documento.toLowerCase()) + "%");
    }

}
