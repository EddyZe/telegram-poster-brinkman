package ru.eddyz.telegramposterbrinkman.bot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.eddyz.telegramposterbrinkman.util.handlers.CallBackQueryHandler;
import ru.eddyz.telegramposterbrinkman.util.handlers.ChatMemberHandler;
import ru.eddyz.telegramposterbrinkman.util.handlers.CommandHandler;


@Component
@Getter
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final String botUsername;

    private final CommandHandler commandHandler;
    private final ChatMemberHandler chatMemberHandler;
    private final CallBackQueryHandler callBackQueryHandler;

    public TelegramBot(@Value("${telegram.bot.token}") String token,
                       @Value("${telegram.bot.username}") String botUsername,
                       CommandHandler commandHandler, ChatMemberHandler chatMemberHandler, CallBackQueryHandler callBackQueryHandler) {
        super(token);
        this.botUsername = botUsername;
        this.commandHandler = commandHandler;
        this.chatMemberHandler = chatMemberHandler;
        this.callBackQueryHandler = callBackQueryHandler;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMyChatMember()) {
            var chatMember = update.getMyChatMember();
            chatMemberHandler.handle(chatMember);
        }

        if (update.hasMessage()) {
            try {
                var message = update.getMessage();
                execute(commandHandler.handel(message));
            } catch (TelegramApiException e) {
                log.error("Ошибка при обработке message: %s".formatted(e.getMessage()));
            }
        } else if (update.hasCallbackQuery()) {
            try {
                var callback = update.getCallbackQuery();
                execute(callBackQueryHandler.handel(callback));
            } catch (TelegramApiException e) {
                log.error("Ошибка при обработки кнопки: %s".formatted(e.getMessage()));
            }
        }
    }

}
