package com.eureka.challenges.stockmarket.domain.ws;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class StockMarketServiceResponse {

    @JsonProperty("Meta Data")
    private MetaData metaData;

    @JsonProperty("Time Series (Daily)")
    private Map<String, TimeSeriesDaily> timeSeriesDaily;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public Map<String, TimeSeriesDaily> getTimeSeriesDaily() {
        return timeSeriesDaily;
    }

    public void setTimeSeriesDaily(Map<String, TimeSeriesDaily> timeSeriesDaily) {
        this.timeSeriesDaily = timeSeriesDaily;
    }

}
