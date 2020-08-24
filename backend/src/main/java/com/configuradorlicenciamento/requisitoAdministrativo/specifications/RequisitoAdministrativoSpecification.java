package com.configuradorlicenciamento.requisitoAdministrativo.specifications;

import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.requisitoAdministrativo.models.RequisitoAdministrativo;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class RequisitoAdministrativoSpecification {

    public static Specification<RequisitoAdministrativo> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

}
