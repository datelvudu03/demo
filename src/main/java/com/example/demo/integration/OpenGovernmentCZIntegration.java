package com.example.demo.integration;

import com.example.demo.dto.GeocodeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenGovernmentCZIntegration {

    private final RestClient restClient = RestClient.create();

    private final String API_KEY = "4b875276ca93c7e2bcf17c447a422107";

    String apiKey = "YOUR_API_KEY";

    private final String URL_BASE = "https://geokeo.com/geocode/v1/search.php?q={query}&api={apiKey}";

    public GeocodeResponse getGeocodeInfo(String query) {
        System.out.println("getGeocodeInfo called");
        ResponseEntity<GeocodeResponse> response = restClient.get()
                .uri(URL_BASE, query, apiKey)
                .retrieve()
                .toEntity(GeocodeResponse.class);

        log.info("{}", response.getBody());

        if (HttpStatusCode.valueOf(204).isSameCodeAs(response.getStatusCode())){
            System.out.println("getGeocodeInfo OK");
            return response.getBody();
        }

        return null;
    }

}
