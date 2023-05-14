package jp.co.axa.apidemo.util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    // Convert object to byte[] format
    public static byte[] toJson(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(obj);
    }

    // Convert object to json String format
    public static String toString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(obj);
    }
}
