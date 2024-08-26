package ru.eddyz.telegramposterbrinkman.commands.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eddyz.telegramposterbrinkman.commands.StartCommand;
import ru.eddyz.telegramposterbrinkman.util.Sender;
import ru.eddyz.telegramposterbrinkman.util.keyboards.ReplayInit;


@Component
public class StartCommandImpl implements StartCommand {

    @Override
    public BotApiMethod<?> execute(Message message) {
        return Sender.sendMessage(
                message.getChatId(),
                generateResponseMessage(),
                ReplayInit.defaultKey()
        );
    }


    private String generateResponseMessage() {
        return """
                Привет 👋
                C помощью меня Вы сможете постить записи каждый час.
                Для этого придумайте пост и затем добавь группы, в которые нужно постить это сообщение.
                
                После того как создадите пост, не забудь отметить его активным.""";

    }
}
