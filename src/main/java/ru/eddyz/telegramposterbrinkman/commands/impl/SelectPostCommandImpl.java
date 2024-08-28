package ru.eddyz.telegramposterbrinkman.commands.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eddyz.telegramposterbrinkman.commands.SelectPostCommand;
import ru.eddyz.telegramposterbrinkman.services.PostService;
import ru.eddyz.telegramposterbrinkman.util.Sender;
import ru.eddyz.telegramposterbrinkman.util.TextHelper;
import ru.eddyz.telegramposterbrinkman.util.enums.PostType;
import ru.eddyz.telegramposterbrinkman.util.keyboards.InlineInit;


@Component
public class SelectPostCommandImpl implements SelectPostCommand {

    private final PostService postService;

    public SelectPostCommandImpl(PostService postService) {
        this.postService = postService;
    }

    @Override
    public BotApiMethod<?> execute(Message message) {
        var chatId = message.getChatId();
        var activePost = postService.findByStatus(true);
        var text = message.getText() == null ? message.getCaption() : message.getText();
        var postId = TextHelper.getIdFromText(text);

        if (activePost.isPresent()) {
            if (postId.equals(activePost.get().getId())) {
                activePost.get().setStatus(false);
                postService.save(activePost.get());
                if (activePost.get().getPostType() == PostType.TEXT)
                    return Sender.editMessageText(
                        message,
                        TextHelper.generatePostMessage(activePost.get()),
                        InlineInit.postSetting());
                else
                    return Sender.editMessageCaption(
                            message,
                            TextHelper.generatePostMessage(activePost.get()),
                            InlineInit.postSetting());

            }

            activePost.get().setStatus(false);
            postService.save(activePost.get());
        }

        var newActivePost = postService.findById(postId);

        if (newActivePost.isEmpty())
            return Sender.sendMessage(
                    chatId,
                    "Пост не найден. Возможно вы его уже удалили",
                    null
            );

        newActivePost.get().setStatus(true);
        postService.save(newActivePost.get());

        if (newActivePost.get().getPostType() != PostType.TEXT)
            return Sender.editMessageCaption(
                    message,
                    TextHelper.generatePostMessage(newActivePost.get()),
                    InlineInit.postSetting()
            );

        return Sender.editMessageText(
                message,
                TextHelper.generatePostMessage(newActivePost.get()),
                InlineInit.postSetting()
        );
    }
}
