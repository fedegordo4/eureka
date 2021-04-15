package com.eureka.challenges.stockmarket.domain.tasks;

import com.eureka.challenges.stockmarket.domain.models.User;
import com.eureka.challenges.stockmarket.domain.ws.ServiceResponse;

public interface StockMarketTask {

    User createUser(String firstName, String lastName, String userName);

    ServiceResponse getStockData(String symbol);
}
