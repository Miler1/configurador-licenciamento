package com.configuradorlicenciamento.taxaadministrativa.specifications;

import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.parametro.models.Parametro;
import com.configuradorlicenciamento.taxa.models.Taxa;
import com.configuradorlicenciamento.taxaadministrativa.models.TaxaAdministrativa;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

public class TaxaAdministrativaSpecification {

    private TaxaAdministrativaSpecification() {}

    public static Specification<TaxaAdministrativa> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<TaxaAdministrativa> ano(Integer ano) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("ano"), ano);
    }
}
