package com.eureka.challenges.stockmarket.service;

import com.eureka.challenges.stockmarket.domain.ws.StockMarketServiceResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class StockMarketService {

    private static final String STOCK_SERVICE_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&outputsize=compact";
    private static final String API_KEY = "X86NOH6II01P7R24";

    public StockMarketServiceResponse getStockMarket(String symbol) {
        StockMarketServiceResponse stockMarketServiceResponse = new StockMarketServiceResponse();
        try {
            HttpEntity<String> request = new HttpEntity<>("", getHeaders());
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<StockMarketServiceResponse> result = restTemplate.exchange(URI.create(getUrl(symbol)), HttpMethod.GET,
                    request, StockMarketServiceResponse.class);
            stockMarketServiceResponse = result.getBody();
        } catch (Exception e) {
            System.out.println();
        }
        return stockMarketServiceResponse;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("useQueryString", "true");
        return headers;
    }

    private String getUrl(String symbol) {
        return STOCK_SERVICE_URL + "&symbol=" + symbol + "&apikey=" + API_KEY;
    }
}
