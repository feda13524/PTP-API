package com.ptp.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ptp.api.model.parse.*;
import com.ptp.api.model.price.*;

@RestController
public class Controller {
    private final AnalysisService analysisService;
    public Controller(AnalysisService analysisService){ this.analysisService = analysisService; }

    @GetMapping("/test")
    public String test() {
        return "<div align=center><h1>ParseThePrice API</h1><h3>✅ Сервер работает!<br><br><a href=\"https://github.com/feda13524/ParseThePrice\">GitHub</a> | <a href=\"https://t.me/feda13524\">Telegram</a></h3></div>";
    }
    
    @PostMapping("/price")
    public PriceResponse price(@RequestBody PriceRequest request) {
        PriceResponse response = new PriceResponse();
        Double price = Parser.findPrice(request.getUrl());
        if (price == null) response.setSuccessful(false);
        else {
            response.setSuccessful(true);
            response.setPrice(price);
        }
        return response;
    }

    @PostMapping("/parse")
    public ParseResponse parse(@RequestBody ParseRequest request) {
        ParseResponse response = new ParseResponse();
        String siteText = Parser.getRawText(request.getUrl());
        String rawResponse = analysisService.analyze(request.getMessage(), siteText).strip();
        if (rawResponse == null || rawResponse.isEmpty() || rawResponse.charAt(0) == '!'){
            response.setSuccessful(false);
        }
        else {
            response.setSuccessful(true);
            response.setResult(rawResponse);
        }
        return response;
    }
}