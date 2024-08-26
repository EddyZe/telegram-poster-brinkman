package ru.eddyz.telegramposterbrinkman.util.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface CommandHandler {

    BotApiMethod<?> handel(Message message);

}
