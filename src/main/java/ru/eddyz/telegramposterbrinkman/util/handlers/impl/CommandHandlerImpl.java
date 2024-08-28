package ru.eddyz.telegramposterbrinkman.util.handlers.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eddyz.telegramposterbrinkman.commands.CreatePostCommand;
import ru.eddyz.telegramposterbrinkman.commands.ShowPostsCommand;
import ru.eddyz.telegramposterbrinkman.commands.StartCommand;
import ru.eddyz.telegramposterbrinkman.data.UserData;
import ru.eddyz.telegramposterbrinkman.util.Sender;
import ru.eddyz.telegramposterbrinkman.util.enums.ReplayButton;
import ru.eddyz.telegramposterbrinkman.util.handlers.CommandHandler;


@Component
public class CommandHandlerImpl implements CommandHandler {

    private final String adminUsername;

    private final StartCommand startCommand;
    private final CreatePostCommand createPostCommand;
    private final ShowPostsCommand showPostsCommand;

    public CommandHandlerImpl(@Value("${telegram.bot.admin}") String adminUsername, StartCommand startCommand, CreatePostCommand createPostCommand, ShowPostsCommand showPostsCommand) {
        this.adminUsername = adminUsername;
        this.startCommand = startCommand;
        this.createPostCommand = createPostCommand;
        this.showPostsCommand = showPostsCommand;
    }

    @Override
    public BotApiMethod<?> handel(Message message){

        if (message.getChat().getUserName() == null && message.getChat().isUserChat()
            || !message.getChat().getUserName().equals(adminUsername) && message.getChat().isUserChat())
            return Sender.sendMessage(
                    message.getChatId(),
                    "Извините, Вам не доступен этот бот!",
                    null);

        if (message.hasText()) {
            String text = message.getText();
            if (text.equals("/start")) {
                UserData.clear(message.getChatId());
                return startCommand.execute(message);
            }

            if (text.equals(ReplayButton.CREATE_POST.toString())) {
                UserData.clear(message.getChatId());
                return createPostCommand.execute(message);
            }

            if (text.equals(ReplayButton.SELECT_ACTIVE_POST.toString())) {
                UserData.clear(message.getChatId());
                return showPostsCommand.execute(message);
            }
        }

        if (UserData.currentCommand.containsKey(message.getChatId())) {
            var currentCommand = UserData.currentCommand.get(message.getChatId());

            if (currentCommand == ReplayButton.CREATE_POST) {
                return createPostCommand.execute(message);
            }
        }

        return null;
    }
}
