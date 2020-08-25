package com.configuradorlicenciamento.requisitoAdministrativo.specifications;

import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.documento.models.Documento;
import com.configuradorlicenciamento.licenca.models.Licenca;
import com.configuradorlicenciamento.requisitoAdministrativo.models.RequisitoAdministrativo;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Subquery;

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

    public static Specification<RequisitoAdministrativo> documentoAndLicenca(Integer documento, Integer licenca){

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("documento"), documento),
                    criteriaBuilder.equal(root.get("licenca"), licenca));
    }

}
