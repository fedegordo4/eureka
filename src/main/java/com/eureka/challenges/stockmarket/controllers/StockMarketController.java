package com.eureka.challenges.stockmarket.controllers;

import com.eureka.challenges.stockmarket.domain.models.User;
import com.eureka.challenges.stockmarket.domain.tasks.StockMarketTask;
import com.eureka.challenges.stockmarket.domain.ws.ServiceResponse;
import com.eureka.challenges.stockmarket.domain.ws.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class StockMarketController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private StockMarketTask stockMarketTask;

    StockMarketController(StockMarketTask stockMarketTask) {
        this.stockMarketTask = stockMarketTask;
    }

    @PostMapping("/users/signup")
    public User createUser(@RequestBody UserRequest userRequest) {
        logger.info("API Call=/users/signup,firstName={}, lastName={}, email={}", userRequest.getFirstName(),
                userRequest.getLastName(), userRequest.getEmail());
        return stockMarketTask.createUser(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail());
    }

    @GetMapping("/stock")
    public ServiceResponse createUser(@RequestParam("symbol") String symbol) {
        logger.info("API Call=/stock, symbol={}", symbol);
        return stockMarketTask.getStockData(symbol);
    }
}
