package com.ptp.api;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {
    private final ChatModel chatModel;
    private static final int INPUT_LEN = 20_000;
    private static final int OUTPUT_LEN = 1000;

    public AnalysisService(ChatModel chatModel){ this.chatModel = chatModel; }

    public String analyze(String message, String siteText){
        String promptText = "Ответ дай преимущественно на русском, КРОМЕ тех случаев, когда ответ подразумевает другие языки/символы. Найди, пожалуйста, в необработанном тексте сайта информацию и кратко(!!!!!!!!!!!!!!!!!!!!!!ОГРАНИЧЕНИЕ НЕ БОЛЕЕ 1000 СИМВОЛОВ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!) изложи её СЫРЫМ текстом, !!НЕ ИСПОЛЬЗУЙ MARKDOWN РАЗМЕТКУ!!НЕ ИСПОЛЬЗУЙ ЖИРНЫЙ ШРИФТ, ПОДЧЕРКИВАНИЯ И ТД!!!!!!!!!!!!!!!! Найди информацию по запросу:";

        UserMessage userMessage = new UserMessage(promptText +
            " \"" + message + "\"\n\nТекст сайта:\n" + siteText.substring(0, Math.min(siteText.length(), INPUT_LEN)));

        Prompt prompt = new Prompt(userMessage);
        String result = chatModel.call(prompt).getResult().getOutput().getText().strip();
        return result.substring(0, Math.min(result.length(), OUTPUT_LEN));
    }
}
