//package org.example.utils;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//
//import java.io.IOException;
//import java.util.List;
//
//public class BooleanDeserializer extends JsonDeserializer<Boolean> {
//
//    @Override
//    public Boolean deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
//
//        if (List.of("1", "active", "true", "enabled").contains(p.getText())) {
//            return Boolean.TRUE;
//        }
//        else if (List.of("0", "inactive", "false", "disabled").contains(p.getText())) {
//            return Boolean.FALSE;
//        }
//        return null;
//    }
//}