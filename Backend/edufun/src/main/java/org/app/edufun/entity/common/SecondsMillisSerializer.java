package org.app.edufun.entity.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class SecondsMillisSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // Convert milliseconds to seconds and milliseconds
        long seconds = value / 1000;
        long milliseconds = value % 1000;

        // Write the seconds and milliseconds as a JSON object or string
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("seconds", seconds);
        jsonGenerator.writeNumberField("milliseconds", milliseconds);
        jsonGenerator.writeEndObject();
    }
}

