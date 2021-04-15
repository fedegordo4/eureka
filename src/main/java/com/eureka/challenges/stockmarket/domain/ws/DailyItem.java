package com.eureka.challenges.stockmarket.domain.ws;

public class DailyItem {
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }



    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }
}
