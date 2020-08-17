package com.rrodriguez.glide.bigcorp.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.rrodriguez.glide.bigcorp.model.dao.OfficeDAO;

import java.io.IOException;

public class OfficeSerializer extends StdSerializer<OfficeDAO> {

    public OfficeSerializer() {
        this(null);
    }

    public OfficeSerializer(Class<OfficeDAO> t) {
        super(t);
    }

    @Override
    public void serialize(OfficeDAO officeDAO,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if (officeDAO == null) {
            jsonGenerator.writeNull();
        } else if (officeDAO.isPrimitive()) {
            jsonGenerator.writeNumber(officeDAO.getId());
        } else {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", officeDAO.getId());
            jsonGenerator.writeStringField("address", officeDAO.getAddress());
            jsonGenerator.writeStringField("city", officeDAO.getCity());
            jsonGenerator.writeStringField("country", officeDAO.getCountry());
            jsonGenerator.writeEndObject();
        }

    }
}
