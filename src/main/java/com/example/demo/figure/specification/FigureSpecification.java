package com.example.demo.figure.specification;

import com.example.demo.figure.model.Figure;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FigureSpecification {
    private final static List<String> exclude = new ArrayList<>(List.of("type", "size", "sort", "page"));

    public static Specification<Figure> bySpecificAttribute(Map<String, String> attributes, Class<? extends Figure> cls) {
        return (root, query, criteriaBuilder) -> {
            if (attributes == null) {
                return criteriaBuilder.conjunction();
            }

            List<Predicate> p = new ArrayList<>();

            String area = attributes.get("area");
            if (area != null) {
                p.add(criteriaBuilder.equal(root.get("area"), area));
            }
            String perimeter = attributes.get("perimeter");
            if (perimeter != null) {
                p.add(criteriaBuilder.equal(root.get("perimeter"), perimeter));
            }

            String type = attributes.get("type");
            if (type == null || cls == null) {
                return criteriaBuilder.and(p.toArray(Predicate[]::new));
            }

            p.add(criteriaBuilder.equal(root.get("type"), type));

            for (Map.Entry<String, String> attribute : attributes.entrySet()) {

                if (!exclude.contains(attribute.getKey())) {
                    Path<Number> path = criteriaBuilder.treat(root, cls).get(attribute.getKey());

                    p.add(criteriaBuilder.equal(path, attribute.getValue()));
                }
            }

            return criteriaBuilder.and(p.toArray(Predicate[]::new));
        };
    }
}
