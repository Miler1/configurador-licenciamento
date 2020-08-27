package com.configuradorlicenciamento.requisitoadministrativo.specifications;

import com.configuradorlicenciamento.configuracao.utils.StringUtil;
import com.configuradorlicenciamento.requisitoadministrativo.models.RequisitoAdministrativo;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

@AllArgsConstructor
public class RequisitoAdministrativoSpecification {

    public static Specification<RequisitoAdministrativo> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<RequisitoAdministrativo> baseJoin(String coluna, String referenced, String filter){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.join(coluna, JoinType.INNER).get(referenced))),
                "%" + StringUtil.removeAccents(filter.toLowerCase()) + "%");
    }

    public static Specification<RequisitoAdministrativo> documento(String documento) {
        return baseJoin("documento", "nome", documento);
    }

    public static Specification<RequisitoAdministrativo> licencaNome(String licenca){
        return baseJoin("licenca", "nome", licenca);
    }

    public static Specification<RequisitoAdministrativo> licencaSigla(String licenca){

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.join("licenca", JoinType.INNER).get("sigla"))),
                "%" + StringUtil.removeAccents(licenca.toLowerCase()) + "%");
    }

    public static Specification<RequisitoAdministrativo> documentoAndLicenca(Integer documento, Integer licenca){

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("documento"), documento),
                    criteriaBuilder.equal(root.get("licenca"), licenca));
    }

}
