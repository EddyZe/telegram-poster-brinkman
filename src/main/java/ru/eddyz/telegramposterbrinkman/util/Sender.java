package ru.eddyz.telegramposterbrinkman.util;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class Sender {

    public static SendMessage sendMessage(Long chatId, String text, ReplyKeyboard keyboard) {
        SendMessage sendMessage = SendMessage.builder()
                .text(text)
                .chatId(chatId)
                .parseMode(ParseMode.HTML)
                .build();



        if (keyboard != null)
            sendMessage.setReplyMarkup(keyboard);

        return sendMessage;
    }
}
