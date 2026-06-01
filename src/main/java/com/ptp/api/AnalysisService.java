package com.ptp.api;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {
    private final ChatModel chatModel;

    public AnalysisService(ChatModel chatModel){ this.chatModel = chatModel; }

    public String analyze(String message, String siteText){
        String promptText = "";
        UserMessage userMessage = new UserMessage(promptText +
            " \"" + message + "\"\n\nТекст сайта:\n" + siteText);
        Prompt prompt = new Prompt(userMessage);
        return chatModel.call(prompt).getResult().getOutput().getText();
    }
}
