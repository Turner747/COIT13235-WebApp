package com.optimed.webapp.mappper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectMapper {
    private static final ModelMapper model_mapper;
    static {
        model_mapper = new ModelMapper();
        model_mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    private ObjectMapper() {
    }
    public static <D, T> D map(final T entity, Class<D> outClass) {
        return model_mapper.map(entity, outClass);
    }
    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }
}
