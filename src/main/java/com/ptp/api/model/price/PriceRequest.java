package com.ptp.api.model.price;

public class PriceRequest {
    private String url;

    public PriceRequest() {}
    public PriceRequest(String url) { this.url = url; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}