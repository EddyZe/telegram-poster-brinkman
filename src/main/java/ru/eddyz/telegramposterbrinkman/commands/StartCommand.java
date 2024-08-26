package ru.eddyz.telegramposterbrinkman.commands;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface StartCommand {

    BotApiMethod<?> execute (Message message);
}
