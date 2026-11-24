package com.example.demo.figure.repository;

import com.example.demo.figure.model.Figure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FigureRepository extends JpaRepository<Figure, Long>, JpaSpecificationExecutor<Figure> {
    Page<Figure> findAll(Specification<Figure> spec, Pageable pageable);
}
