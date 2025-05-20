package com.programacaoweb.semibiblioteca.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class RespostaException {

    public static void handlerGlobalExepction (Boolean success, Map<String, Object> responseMap, HttpServletResponse response) throws IOException {
        responseMap.put("success", success);
        responseMap.put("timestamp", LocalDateTime.now().toString());
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String formattedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseMap);
        response.getWriter().write(formattedJson);
    }
}
