package com.eureka.challenges.stockmarket.domain.ws;

public class ServiceResponse {

    private String openPrice;
    private String higherPrice;
    private String lowerPrice;
    private String twoDayVariation;

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getHigherPrice() {
        return higherPrice;
    }

    public void setHigherPrice(String higherPrice) {
        this.higherPrice = higherPrice;
    }

    public String getLowerPrice() {
        return lowerPrice;
    }

    public void setLowerPrice(String lowerPrice) {
        this.lowerPrice = lowerPrice;
    }

    public String getTwoDayVariation() {
        return twoDayVariation;
    }

    public void setTwoDayVariation(String twoDayVariation) {
        this.twoDayVariation = twoDayVariation;
    }

}
