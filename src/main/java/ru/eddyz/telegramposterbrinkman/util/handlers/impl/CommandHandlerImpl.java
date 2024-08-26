package ru.eddyz.telegramposterbrinkman.util.handlers.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eddyz.telegramposterbrinkman.commands.StartCommand;
import ru.eddyz.telegramposterbrinkman.util.handlers.CommandHandler;
import ru.eddyz.telegramposterbrinkman.util.Sender;


@Component
public class CommandHandlerImpl implements CommandHandler {

    private final String adminUsername;

    private final StartCommand startCommand;

    public CommandHandlerImpl(@Value("${telegram.bot.admin}") String adminUsername, StartCommand startCommand) {
        this.adminUsername = adminUsername;
        this.startCommand = startCommand;
    }

    @Override
    public BotApiMethod<?> handel(Message message){

        if (message.getChat().getUserName() == null || !message.getChat().getUserName().equals(adminUsername))
            return Sender.sendMessage(
                    message.getChatId(),
                    "Извините, Вам не доступен этот бот!",
                    null);

        if (message.hasText()) {
            String text = message.getText();
            if (text.equals("/start"))
                return startCommand.execute(message);
        }

        return null;
    }
}
