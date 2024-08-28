package ru.eddyz.telegramposterbrinkman.util.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallBackQueryHandler {

    BotApiMethod<?> handel(CallbackQuery callbackQuery);

}
