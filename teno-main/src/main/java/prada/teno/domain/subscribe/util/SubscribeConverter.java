package prada.teno.domain.subscribe.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import prada.teno.domain.subscribe.dto.SubscribeData;

import javax.persistence.AttributeConverter;
import java.io.IOException;

@Slf4j
public class SubscribeConverter implements AttributeConverter<SubscribeData, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    //Subscribe 객체 -> Json 문자열로 변환
    @Override
    public String convertToDatabaseColumn(SubscribeData subscribeData) {
        try {
            return objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(subscribeData);
        } catch (JsonProcessingException e) {
            log.error("fail to serialize as object in to Json : {}", subscribeData, e);
            throw new RuntimeException(e);
        }
    }

    //Json 문자열 Subscribe 객체로 변환
    @Override
    public SubscribeData convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.registerModule(new JavaTimeModule()).readValue(dbData, SubscribeData.class);
        } catch (IOException e) {
            log.error("fail to deserialize as Json into Object : {}", dbData, e);
            throw new RuntimeException(e);
        }
    }
}
