package com.configuradorlicenciamento.pergunta.specifications;

import com.configuradorlicenciamento.pergunta.models.Pergunta;
import org.springframework.data.jpa.domain.Specification;

public class PerguntaSpecification {

    private PerguntaSpecification() {}

    public static Specification<Pergunta> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<Pergunta> titulo(String titulo) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.get("texto"))),
                                        "%" + StringUtil.removeAccents(titulo.toLowerCase()) + "%");
    }

    public static Specification<Pergunta> matchTitulo (String titulo) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(
                criteriaBuilder.lower(root.get("texto")), titulo.toLowerCase());
    }

}
