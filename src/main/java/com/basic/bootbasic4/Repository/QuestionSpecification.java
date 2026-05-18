package com.basic.bootbasic4.Repository;

import com.basic.bootbasic4.entity.Question;
import com.basic.bootbasic4.entity.QuestionCategory;
import com.basic.bootbasic4.entity.QuestionPetType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class QuestionSpecification {

    private QuestionSpecification() {}

    public static Specification<Question> withCondition(String keyword, QuestionCategory category, QuestionPetType petType) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isBlank()) {
                String pattern = "%" + keyword.trim() + "%";
                predicates.add(builder.or(
                    builder.like(root.get("title"), pattern),
                    builder.like(root.get("content"), pattern)
                ));
            }

            if (category != null) {
                predicates.add(builder.equal(root.get("category"), category));
            }

            if (petType != null && petType != QuestionPetType.ALL) {
                predicates.add(builder.equal(root.get("petType"), petType));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
