package ru.eddyz.telegramposterbrinkman.commands.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eddyz.telegramposterbrinkman.commands.EditPostModeCommand;
import ru.eddyz.telegramposterbrinkman.services.PostService;
import ru.eddyz.telegramposterbrinkman.util.Sender;
import ru.eddyz.telegramposterbrinkman.util.TextHelper;
import ru.eddyz.telegramposterbrinkman.util.enums.PostMode;
import ru.eddyz.telegramposterbrinkman.util.enums.PostType;
import ru.eddyz.telegramposterbrinkman.util.keyboards.InlineInit;


@Component
public class EditPostModeCommandImpl implements EditPostModeCommand {

    private final PostService postService;

    public EditPostModeCommandImpl(PostService postService) {
        this.postService = postService;
    }

    @Override
    public BotApiMethod<?> execute(Message message, PostMode mode) {
        var chatId = message.getChatId();
        var text = message.getText() == null ? message.getCaption() : message.getText();
        var postId = TextHelper.getIdFromText(text);
        var post = postService.findById(postId);

        if (post.isEmpty())
            return Sender.sendMessage(
                    chatId,
                    "Пост не найден. Возможно он уже удален",
                    null
            );

        post.get().setPostMode(mode);
        postService.save(post.get());

        if (post.get().getPostType() == PostType.TEXT)
            return Sender.editMessageText(
                    message,
                    TextHelper.generatePostMessage(post.get()),
                    InlineInit.postSetting()
            );
        else
            return Sender.editMessageCaption(
                    message,
                    TextHelper.generatePostMessage(post.get()),
                    InlineInit.postSetting()
            );
    }
}
