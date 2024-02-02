package org.app.edufun.entity.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.OffsetDateTime;
import java.time.ZoneId;
public class InstantSerializer extends JsonSerializer<Instant> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(Instant instant, JsonGenerator generator, SerializerProvider provider)
            throws IOException {
        OffsetDateTime indianDateTime = instant.atZone(ZoneId.of("Asia/Kolkata")).toOffsetDateTime();
        String formattedInstant = formatter.format(indianDateTime);
        generator.writeString(formattedInstant);
    }
}






