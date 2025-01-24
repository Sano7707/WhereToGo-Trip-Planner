//package com.example.recommendationservice.service.adapter.utility;
//
//import com.example.recommendationservice.dto.PlaceDTO;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonParser.Feature;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//import java.util.List;
//
//@Component
//public class JsonUtils {
//
//    private static  ObjectMapper mapper = new ObjectMapper();
//
//    static String string = "```json\n[\n  {\n    \"place_name\": \"Vatican Museums\",\n    \"description\": \"Home to the Sistine Chapel and an unparalleled collection of art and artifacts from throughout history.\",\n    \"website_link\": \"https://www.museivaticani.va/\"\n  },\n  {\n    \"place_name\": \"Borghese Gallery and Museum\",\n    \"description\": \"Houses masterpieces by Bernini, Caravaggio, and Raphael, among others.  Requires timed entry tickets.\",\n    \"website_link\": \"https://www.galleriaborghese.it/en/\"\n  },\n  {\n    \"place_name\": \"Capitoline Museums\",\n    \"description\": \"A complex of museums showcasing Roman history and art, including sculptures and artifacts from the Roman Forum.\",\n    \"website_link\": \"https://www.museicapitolini.org/en\"\n  },\n  {\n    \"place_name\": \"National Roman Museum\",\n    \"description\": \"A network of museums exhibiting Roman art and archaeology from various periods.  Check which locations best suit your interests.\",\n    \"website_link\": \"https://www.museonazionaleromano.it/en\" \n  },\n  {\n    \"place_name\": \"Palazzo Massimo alle Terme\",\n    \"description\": \"Part of the National Roman Museum, featuring stunning frescoes and mosaics.\",\n    \"website_link\": \"https://www.museonazionaleromano.it/en/palazzo-massimo-alle-terme\"\n  },\n  {\n    \"place_name\": \"Doria Pamphilj Gallery\",\n    \"description\": \"A private art collection with works from the Renaissance to the Baroque periods.\",\n    \"website_link\": \"https://www.galleriadoriapamphilj.it/en/\"\n  }\n]\n```\n";
//
//
//    public static String extractImageUrlFromPexelsResponse(String json) {
//        try {
//            JsonNode rootNode = mapper.readTree(json);
//            return rootNode.path("photos").get(0).path("src").path("original").asText();
//        } catch (Exception e) {
//            return null; // Return null if image not found
//        }
//    }
//
//
//    private static String cleanGeminiResponse(String input) {
//        if (input.startsWith("```json")) {
//            input = input.substring("```json".length());
//        }
//        if (input.endsWith("```")) {
//            input = input.substring(0, input.length() - "```".length());
//        }
//        input = input.replace("```json\\n","");
//        input = input.replace("\\n","");
//        input = input.replace("\\","");
//        input = input.replace("```","");
//        input = input.substring(0,input.lastIndexOf("]") + 1);
//        return input.trim();
//    }
//
//
//    public static List<PlaceDTO> convertJsonToPlaceDTOList(String jsonString) {
//        jsonString = cleanGeminiResponse(jsonString);
//        System.out.println(jsonString);
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            return objectMapper.readValue(
//                jsonString,
//                objectMapper.getTypeFactory().constructCollectionType(List.class, PlaceDTO.class)
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static void main(String[] args) {
//
//        System.out.println(convertJsonToPlaceDTOList(string));
//    }
//
//}
//
