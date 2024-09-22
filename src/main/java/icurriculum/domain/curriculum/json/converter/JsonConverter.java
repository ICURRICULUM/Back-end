package icurriculum.domain.curriculum.json.converter;

import jakarta.persistence.AttributeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 제네릭 JSON Attribute Converter
 * Todo 예외 추후 정의
 *
 * @param <T> 변환할 엔티티의 클래스 타입
 */

public class JsonConverter<T> implements AttributeConverter<T, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> clazz;

    public JsonConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}
