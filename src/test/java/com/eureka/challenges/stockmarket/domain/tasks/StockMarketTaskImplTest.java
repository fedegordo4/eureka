package com.eureka.challenges.stockmarket.domain.tasks;

import com.eureka.challenges.stockmarket.domain.exceptions.InvalidUserException;
import com.eureka.challenges.stockmarket.domain.models.User;
import com.eureka.challenges.stockmarket.domain.ws.ServiceResponse;
import com.eureka.challenges.stockmarket.domain.ws.StockMarketServiceResponse;
import com.eureka.challenges.stockmarket.domain.ws.TimeSeriesDaily;
import com.eureka.challenges.stockmarket.service.StockMarketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class StockMarketTaskImplTest {

    private StockMarketTaskImpl stockMarketTask;
    private StockMarketService service;


    @BeforeEach
    void setup() {
        service = Mockito.mock(StockMarketService.class);
        stockMarketTask = new StockMarketTaskImpl(service);
    }

    @Test
    void testCreateUserEmptyNameShouldThrowException() {

        Exception exception = assertThrows(InvalidUserException.class,
                () -> stockMarketTask.createUser("", "Jordan", "email@test.com"));

        String expectedMessage = "firstName or lastName can't be empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreateUserEmptyLastNameShouldThrowException() {

        Exception exception = assertThrows(InvalidUserException.class,
                () -> stockMarketTask.createUser("Michael", "", "email@test.com"));

        String expectedMessage = "firstName or lastName can't be empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreateUserInvalidEmailShouldThrowException() {

        Exception exception = assertThrows(InvalidUserException.class,
                () -> stockMarketTask.createUser("Michael", "Jordan", "em.com"));

        String expectedMessage = "Invalid email format";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreateValidUserShouldReturnBearerToken() {
        User user = stockMarketTask.createUser("Michael", "Jordan", "em@em.com");
        assertTrue(user.getToken().contains("Bearer"));
    }

    @Test
    void testGetServiceData(){
        StockMarketServiceResponse serviceResponse = new StockMarketServiceResponse();
        Map<String,TimeSeriesDaily> timeSeriesDaily = new LinkedHashMap<>();
        TimeSeriesDaily timeSeriesDailyItem1 = buildTimeSeriesItem("100.00","105.00","98.00","102.00");
        TimeSeriesDaily timeSeriesDailyItem2 = buildTimeSeriesItem("102.00","109.00","100.00","101.00");
        timeSeriesDaily.put("2021-04-12",timeSeriesDailyItem1);
        timeSeriesDaily.put("2021-04-11",timeSeriesDailyItem2);

        serviceResponse.setTimeSeriesDaily(timeSeriesDaily);
        Mockito.when(service.getStockMarket(any())).thenReturn(serviceResponse);

        ServiceResponse response = stockMarketTask.getStockData("FB");

        assertResponseValues(response);
    }


    private void assertResponseValues(ServiceResponse response) {
        assertEquals("105.00", response.getHigherPrice());
        assertEquals("98.00", response.getLowerPrice());
        assertEquals("102.00", response.getOpenPrice());
        assertEquals("-2.0", response.getTwoDayVariation());
    }

    private TimeSeriesDaily buildTimeSeriesItem(String close, String high, String low, String open){
        TimeSeriesDaily timeSeriesDailyItem = new TimeSeriesDaily();
        timeSeriesDailyItem.setClose(close);
        timeSeriesDailyItem.setHigh(high);
        timeSeriesDailyItem.setLow(low);
        timeSeriesDailyItem.setOpen(open);
        return timeSeriesDailyItem;
    }
}