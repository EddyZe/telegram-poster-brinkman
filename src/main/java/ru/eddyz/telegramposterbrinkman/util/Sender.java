package ru.eddyz.telegramposterbrinkman.util;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
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

    public static SendPhoto sendPhoto(Long chatId, String caption, String fileId, ReplyKeyboard keyboard) {
        SendPhoto sendPhoto = SendPhoto.builder()
                .caption(caption)
                .chatId(chatId)
                .parseMode(ParseMode.HTML)
                .photo(new InputFile(fileId))
                .build();

        if (keyboard != null) {
            sendPhoto.setReplyMarkup(keyboard);
        }

        return sendPhoto;
    }

    public static SendVideo sendVideo (Long chatId, String caption, String fileId, ReplyKeyboard keyboard) {
        SendVideo sendVideo = SendVideo.builder()
                .caption(caption)
                .chatId(chatId)
                .parseMode(ParseMode.HTML)
                .video(new InputFile(fileId))
                .build();

        if (keyboard != null) {
            sendVideo.setReplyMarkup(keyboard);
        }
        return sendVideo;
    }

    public static SendAnimation sendAnimation(Long chatId, String caption, String fileId, ReplyKeyboard keyboard) {
        SendAnimation sendAnimation = SendAnimation.builder()
                .animation(new InputFile(fileId))
                .chatId(chatId)
                .parseMode(ParseMode.HTML)
                .caption(caption)
                .build();

        if (keyboard != null)
            sendAnimation.setReplyMarkup(keyboard);

        return sendAnimation;
    }

    public static EditMessageText editMessageText(Message message, String text, InlineKeyboardMarkup replyKeyboard) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setText(text);
        editMessageText.setChatId(message.getChatId());
        if (replyKeyboard != null)
            editMessageText.setReplyMarkup(replyKeyboard);
        editMessageText.enableHtml(true);
        return editMessageText;
    }

    public static EditMessageCaption editMessageCaption(Message message, String text, InlineKeyboardMarkup replyKeyboard) {
        EditMessageCaption editMessageCaption = new EditMessageCaption();
        editMessageCaption.setCaption(text);
        editMessageCaption.setMessageId(message.getMessageId());
        editMessageCaption.setChatId(message.getChatId());
        editMessageCaption.setParseMode(ParseMode.HTML);
        if (replyKeyboard != null)
            editMessageCaption.setReplyMarkup(replyKeyboard);

        return editMessageCaption;
    }

    public static DeleteMessage deleteMessage(Message message) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(message.getMessageId());
        deleteMessage.setChatId(message.getChatId());
        return deleteMessage;
    }

}
