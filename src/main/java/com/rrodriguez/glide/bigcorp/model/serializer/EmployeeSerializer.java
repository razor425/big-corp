package com.rrodriguez.glide.bigcorp.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.rrodriguez.glide.bigcorp.model.dao.DepartmentDAO;
import com.rrodriguez.glide.bigcorp.model.dao.EmployeeDAO;
import com.rrodriguez.glide.bigcorp.model.dao.OfficeDAO;

import java.io.IOException;

public class EmployeeSerializer extends StdSerializer<EmployeeDAO> {

    private OfficeSerializer officeSerializer = new OfficeSerializer(OfficeDAO.class);
    private DepartmentSerializer departmentSerializer = new DepartmentSerializer(DepartmentDAO.class);

    public EmployeeSerializer() {
        this(null);
    }

    public EmployeeSerializer(Class<EmployeeDAO> t) {
        super(t);
    }

    @Override
    public void serialize(EmployeeDAO employeeDAO,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        if (employeeDAO == null || employeeDAO.getId() == null) {
            jsonGenerator.writeNull();
        } else if (employeeDAO.isPrimitive()) {
            jsonGenerator.writeNumber(employeeDAO.getId());
        } else {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", employeeDAO.getId());
            jsonGenerator.writeStringField("first", employeeDAO.getFirst());
            jsonGenerator.writeStringField("last", employeeDAO.getLast());

            //draw manager
            jsonGenerator.writeFieldName("manager");
            serialize(employeeDAO.getManager(), jsonGenerator, serializerProvider);
            //draw office
            jsonGenerator.writeFieldName("office");
            officeSerializer.serialize(employeeDAO.getOffice(), jsonGenerator, serializerProvider);
            //draw department
            jsonGenerator.writeFieldName("department");
            departmentSerializer.serialize(employeeDAO.getDepartment(), jsonGenerator, serializerProvider);

            jsonGenerator.writeEndObject();
        }
    }
}
