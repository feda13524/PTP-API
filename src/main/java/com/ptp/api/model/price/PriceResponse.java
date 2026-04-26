package com.ptp.api.model.price;

public class PriceResponse {
    private double price = 0;
    private boolean successful;
    
    public PriceResponse() {}
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public boolean isSuccessful() { return successful; }
    public void setSuccessful(boolean successful) { this.successful = successful; }
}