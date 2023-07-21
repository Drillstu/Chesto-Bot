package com.bot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.bson.Document;

public class ObjectToDocument {

   public static Document toDocument(Object obj) throws JsonProcessingException {
       // Object to JSON
       ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
       String jsonObj = writer.writeValueAsString(obj);
       // JSON to Document
       return Document.parse(jsonObj);
   }

}
