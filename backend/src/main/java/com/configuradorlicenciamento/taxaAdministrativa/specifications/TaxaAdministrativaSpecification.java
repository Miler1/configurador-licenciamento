package com.configuradorlicenciamento.taxaAdministrativa.specifications;

import com.configuradorlicenciamento.taxaAdministrativa.models.TaxaAdministrativa;
import org.springframework.data.jpa.domain.Specification;

public class TaxaAdministrativaSpecification {

    private TaxaAdministrativaSpecification() {}

    public static Specification<TaxaAdministrativa> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<TaxaAdministrativa> ano(String ano) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("ano").as(String.class), "%" + ano + "%");
    }
}
