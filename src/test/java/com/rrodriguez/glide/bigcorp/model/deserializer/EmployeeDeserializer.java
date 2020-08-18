package com.rrodriguez.glide.bigcorp.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.rrodriguez.glide.bigcorp.model.dto.CustomDepartmentDTO;
import com.rrodriguez.glide.bigcorp.model.dto.CustomEmployeeDTO;

import java.io.IOException;

public class EmployeeDeserializer extends StdDeserializer<CustomEmployeeDTO> {

    public EmployeeDeserializer() {
        this(null);
    }

    public EmployeeDeserializer(Class<CustomEmployeeDTO> vc) {
        super(vc);
    }

    @Override
    public CustomEmployeeDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        Long id = jsonNode.get("id").longValue();
        String first = jsonNode.get("first").textValue();
        String last = jsonNode.get("last").textValue();

        CustomEmployeeDTO result = new CustomEmployeeDTO(id,first,last);

        CustomEmployeeDTO manager;
        JsonNode managerNode = jsonNode.get("manager");

        if(managerNode.isNull()){
            manager = null;
        }else if(managerNode.isInt()){
            manager = new CustomEmployeeDTO((long) managerNode.intValue());
        }else{
            manager = deserialize(jsonParser,deserializationContext);
        }

        result.setManager(manager);

        CustomDepartmentDTO department;
        JsonNode departmentNode = jsonNode.get("department");

        if(departmentNode.isNull()){
            department= null;
        }else if(departmentNode.isInt()){
            department= new CustomDepartmentDTO((long) departmentNode.intValue());
        }else{
            manager = deserialize(jsonParser,deserializationContext);
        }
        return result;
    }
}
