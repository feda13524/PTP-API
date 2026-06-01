package com.ptp.api;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {
    private final ChatModel chatModel;
    private static final int MAX_LEN = 20_000;

    public AnalysisService(ChatModel chatModel){ this.chatModel = chatModel; }

    public String analyze(String message, String siteText){
        String promptText = "Ответ дай на русском языке. Найди, пожалуйста, в необработанном тексте сайта информацию и кратко изложи её сырым текстом, не используя markdown разметку. Найди информацию:";

        UserMessage userMessage = new UserMessage(promptText +
            " \"" + message + "\"\n\nТекст сайта:\n" + siteText.substring(0, Math.min(siteText.length(), MAX_LEN)));

        Prompt prompt = new Prompt(userMessage);
        return chatModel.call(prompt).getResult().getOutput().getText();
    }
}
