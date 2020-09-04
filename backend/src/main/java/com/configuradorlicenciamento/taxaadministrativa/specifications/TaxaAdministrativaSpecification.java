package com.configuradorlicenciamento.taxaadministrativa.specifications;

import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;
import org.springframework.data.jpa.domain.Specification;

public class TaxaAdministrativaSpecification {

    private TaxaAdministrativaSpecification() {}

    public static Specification<TaxaAdministrativa> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<TaxaAdministrativa> ano(Integer ano) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("ano"), ano);
    }
}
