package com.ptp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApp {
	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);

        System.out.println("✅ Сервер запущен!");
        System.out.println("📡 Тестовый эндпоинт: https://api.fedosik.ru/test");

		Parser.initialize();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("🛑 Остановка приложения, закрытие WebDriver...");
            Parser.shutdown();
		}));
	}
}