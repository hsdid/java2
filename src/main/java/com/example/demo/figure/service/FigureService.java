package com.example.demo.figure.service;

import com.example.demo.figure.exception.FigureTypeNotSupportedException;
import com.example.demo.figure.handler.FigureHandler;
import com.example.demo.figure.dto.FigureRequest;
import com.example.demo.figure.dto.response.FigureResponse;
import com.example.demo.figure.model.Figure;
import com.example.demo.figure.repository.FigureRepository;

import com.example.demo.figure.specification.FigureSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class FigureService {
    private final List<FigureHandler> figureHandlers;
    private final FigureRepository figureRepository;

    public FigureService(
            FigureRepository figureRepository,
            List<FigureHandler> figureHandlers
    ) {
        this.figureRepository = figureRepository;
        this.figureHandlers = figureHandlers;
    }

    @Transactional
    public FigureResponse createFigure(FigureRequest figureRequest) {

        FigureHandler handler = getFigureHandler(figureRequest.type());
        Figure figure = handler.createFigure(figureRequest.params());

        figureRepository.save(figure);

        return handler.map(figure);
    }

    @Transactional(readOnly = true)
    public Page<FigureResponse> getFigures(Pageable pageable, String type, Map<String, String> filters) {

        Specification<Figure> specification = null;
        if (type != null) {

            FigureHandler handler = getFigureHandler(type);


            specification = Specification.where(
                    FigureSpecification.bySpecificAttribute(filters, handler.entityClass())
            );

        } else {
            specification = Specification.where(
                    FigureSpecification.bySpecificAttribute(filters, null)
            );
        }


        return figureRepository.findAll(specification, pageable)
                .map(figure ->
                        getFigureHandler(figure.getType()).map(figure)
                );
    }

    private FigureHandler getFigureHandler(String type) {
        return figureHandlers
                .stream()
                .filter(figureHandler -> figureHandler.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new FigureTypeNotSupportedException("Figure type not supported"));
    }
}
