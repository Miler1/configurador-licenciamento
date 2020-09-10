package com.configuradorlicenciamento.pergunta.specifications;

import com.configuradorlicenciamento.pergunta.models.Pergunta;
import org.springframework.data.jpa.domain.Specification;

public class PerguntaSpecification {

    private PerguntaSpecification() {}

    public static Specification<Pergunta> padrao() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public static Specification<Pergunta> titulo(String titulo) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("texto").as(String.class), "%" + titulo + "%");
    }

}
