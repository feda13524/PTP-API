package com.ptp.api.model.parse;

public class ParseRequest {
    private String url;
    private String message;

    public ParseRequest() {}
    public ParseRequest(String url, String message) { this.url = url; this.message = message;}
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
