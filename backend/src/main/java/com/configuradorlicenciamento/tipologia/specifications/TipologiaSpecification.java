package com.configuradorlicenciamento.tipologia.specifications;

import com.configuradorlicenciamento.atividadeCnae.models.AtividadeCnae;
import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.tipologia.models.Tipologia;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class TipologiaSpecification {

    public static Specification<Tipologia> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<Tipologia> codigo(String codigo) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("codigo"), codigo);
    }

}
