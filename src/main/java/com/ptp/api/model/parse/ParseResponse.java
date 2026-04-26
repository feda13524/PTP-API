package com.ptp.api.model.parse;

public class ParseResponse {
    private String result = "";
    private boolean successful;
    
    public ParseResponse() {}
    
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    
    public boolean isSuccessful() { return successful; }
    public void setSuccessful(boolean successful) { this.successful = successful; }
}
