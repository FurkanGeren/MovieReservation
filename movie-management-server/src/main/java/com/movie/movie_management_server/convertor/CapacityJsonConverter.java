package com.movie.movie_management_server.convertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Converter(autoApply = true)
public class CapacityJsonConverter implements AttributeConverter<List<Integer>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting List<Integer> to JSON", e);
        }
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, objectMapper.getTypeFactory().constructCollectionType(List.class, Integer.class));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON to List<Integer>", e);
        }
    }

    public List<Integer> generateCapacityList(Integer capacity) {
        return IntStream.rangeClosed(1, capacity)
                .boxed()
                .collect(Collectors.toList());
    }
}

