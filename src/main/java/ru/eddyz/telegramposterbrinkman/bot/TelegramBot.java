package ru.eddyz.telegramposterbrinkman.bot;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.eddyz.telegramposterbrinkman.util.handlers.ChatMemberHandler;
import ru.eddyz.telegramposterbrinkman.util.handlers.CommandHandler;


@Component
@Getter
public class TelegramBot extends TelegramLongPollingBot {
    private final String botUsername;

    private final CommandHandler commandHandler;
    private final ChatMemberHandler chatMemberHandler;

    public TelegramBot(@Value("${telegram.bot.token}") String token,
                       @Value("${telegram.bot.username}") String botUsername,
                       CommandHandler commandHandler, ChatMemberHandler chatMemberHandler) {
        super(token);
        this.botUsername = botUsername;
        this.commandHandler = commandHandler;
        this.chatMemberHandler = chatMemberHandler;
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMyChatMember()) {
            var chatMember = update.getMyChatMember();
            chatMemberHandler.handle(chatMember);
        }

        if (update.hasMessage()) {
            var message = update.getMessage();
            execute(commandHandler.handel(message));
        }
    }

}
