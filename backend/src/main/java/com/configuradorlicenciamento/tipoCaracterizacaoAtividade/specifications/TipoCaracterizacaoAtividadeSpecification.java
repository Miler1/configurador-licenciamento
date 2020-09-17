package com.configuradorlicenciamento.tipoCaracterizacaoAtividade.specifications;

import com.configuradorlicenciamento.tipoCaracterizacaoAtividade.models.TipoCaracterizacaoAtividade;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class TipoCaracterizacaoAtividadeSpecification {

    public static Specification<TipoCaracterizacaoAtividade> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<TipoCaracterizacaoAtividade> filtrarAtividadesDispensaveis() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("dispensa_licenciamento"));
    }

}
