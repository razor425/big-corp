package com.rrodriguez.glide.bigcorp.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.rrodriguez.glide.bigcorp.model.dao.DepartmentDAO;

import java.io.IOException;

public class DepartmentSerializer extends StdSerializer<DepartmentDAO> {
    protected DepartmentSerializer(Class<DepartmentDAO> t) {
        super(t);
    }

    protected DepartmentSerializer() {
        this(null);
    }

    @Override
    public void serialize(DepartmentDAO departmentDAO,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        if (departmentDAO == null || departmentDAO.getId() == null) {
            jsonGenerator.writeNull();
        } else if (departmentDAO.isPrimitive()) {
            jsonGenerator.writeNumber(departmentDAO.getId());
        } else {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", departmentDAO.getId());
            jsonGenerator.writeStringField("name", departmentDAO.getName());
            //draw department
            jsonGenerator.writeFieldName("superdepartment");
            serialize(departmentDAO.getSuperdepartment(), jsonGenerator, serializerProvider);
            jsonGenerator.writeEndObject();
        }

    }
}
