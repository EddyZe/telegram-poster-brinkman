package ru.eddyz.telegramposterbrinkman.commands;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eddyz.telegramposterbrinkman.util.enums.PostMode;

public interface EditPostModeCommand {

    BotApiMethod<?> execute(Message message, PostMode mode);
}
