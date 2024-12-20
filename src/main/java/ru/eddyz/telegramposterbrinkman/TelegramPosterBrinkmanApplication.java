package ru.eddyz.telegramposterbrinkman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TelegramPosterBrinkmanApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramPosterBrinkmanApplication.class, args);
    }

}
