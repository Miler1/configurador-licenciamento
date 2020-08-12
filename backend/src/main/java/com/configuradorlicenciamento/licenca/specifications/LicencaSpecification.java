package com.configuradorlicenciamento.licenca.specifications;

import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.licenca.models.Licenca;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class LicencaSpecification {

    public static Specification<Licenca> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<Licenca> nome(String nome) {

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.get("nome"))),
                "%" + StringUtil.removeAccents(nome.toLowerCase()) + "%");

    }

    public static Specification<AtividadeCnae> sigla(String sigla) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("sigla")), "%" + sigla.toLowerCase() + "%");
    }

}
