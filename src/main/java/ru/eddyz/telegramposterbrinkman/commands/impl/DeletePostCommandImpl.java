package ru.eddyz.telegramposterbrinkman.commands.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eddyz.telegramposterbrinkman.commands.DeletePostCommand;
import ru.eddyz.telegramposterbrinkman.services.PostService;
import ru.eddyz.telegramposterbrinkman.util.Sender;
import ru.eddyz.telegramposterbrinkman.util.TextHelper;


@Component
public class DeletePostCommandImpl implements DeletePostCommand {

    private final PostService postService;

    public DeletePostCommandImpl(PostService postService) {
        this.postService = postService;
    }

    @Override
    public BotApiMethod<?> execute(Message message) {
        var text =  message.getText() == null ? message.getCaption() : message.getText();
        var postId = TextHelper.getIdFromText(text);
        var post = postService.findById(postId);

        if (post.isEmpty())
            return Sender.sendMessage(
                    message.getChatId(),
                    "Пост не найден, возможно он уже удален!",
                    null);

        postService.deleteById(postId);

        return Sender.deleteMessage(message);
    }
}
