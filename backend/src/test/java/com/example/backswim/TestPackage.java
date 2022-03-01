package com.example.backswim;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class TestPackage {

    protected final String APPLICATION_JSON = "application/json;charset=UTF-8";
    protected final String USER_HEADER = "X-USER-ID";

    protected final String JSON_EMPTY_STRING = "{}";

    protected final ObjectWriter objectWriter = new ObjectMapper().writer();

    protected <T> String toJson(T obj) throws JsonProcessingException {
        return objectWriter.writeValueAsString(obj);
    }
}
