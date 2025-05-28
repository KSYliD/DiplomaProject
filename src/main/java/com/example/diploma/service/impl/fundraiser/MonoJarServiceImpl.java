package com.example.diploma.service.impl.fundraiser;

import com.example.diploma.model.fundraiser.Fundraiser;
import com.example.diploma.service.interfaces.fundraiser.MonoJarService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class MonoJarServiceImpl implements MonoJarService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public MonoJarServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public Fundraiser sendRequest(String jarId, String pc) {
        String url = "https://send.monobank.ua/api/handler";
        Fundraiser fundraiser = null;

        String jsonBody = String.format("{\"c\": \"hello\", \"clientId\": \"%s\", \"Pc\": \"%s\"}", jarId, pc);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return null; //TODO:check this code fragment
        }

        try {
            JsonNode json = objectMapper.readTree(response.getBody());

            fundraiser = Fundraiser.builder()
                    .title(json.path("name").asText())
                    .avatar(json.path("avatar").asText())
                    .description(json.path("description").asText())
                    .targetAmount(json.path("jarGoal").asLong())
                    .currentAmount(json.path("jarAmount").asLong())
                    .fromVolunteer(json.path("isTrusted").asBoolean())
                    .build();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return fundraiser;
    }
}