package ru.eddyz.telegramposterbrinkman.commands.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eddyz.telegramposterbrinkman.commands.CreatePostCommand;
import ru.eddyz.telegramposterbrinkman.data.UserData;
import ru.eddyz.telegramposterbrinkman.entities.Post;
import ru.eddyz.telegramposterbrinkman.services.PostService;
import ru.eddyz.telegramposterbrinkman.util.Sender;
import ru.eddyz.telegramposterbrinkman.util.enums.PostMode;
import ru.eddyz.telegramposterbrinkman.util.enums.PostState;
import ru.eddyz.telegramposterbrinkman.util.enums.PostType;
import ru.eddyz.telegramposterbrinkman.util.enums.ReplayButton;
import ru.eddyz.telegramposterbrinkman.util.keyboards.ReplayInit;


@Component
@RequiredArgsConstructor
public class CreatePostCommandImpl implements CreatePostCommand {

    private final PostService postService;

    @Override
    public BotApiMethod<?> execute(Message message) {
        Long chatId = message.getChatId();

        if (!UserData.currentCommand.containsKey(chatId)) {
            UserData.currentCommand.put(chatId, ReplayButton.CREATE_POST);
            UserData.currentStateUser.put(chatId, PostState.NAME);
            UserData.currentStatePost.put(chatId, Post.builder()
                    .count(0)
                    .postMode(PostMode.ONE)
                    .status(false)
                    .build());
            return Sender.sendMessage(
                    chatId,
                    "Придумайте название посту. (Это нужно только для отображения в списке)",
                    ReplayInit.defaultKey()
            );
        }

        var currentPost = UserData.currentStatePost.get(chatId);
        var currentState = UserData.currentStateUser.get(chatId);

        if (currentState == PostState.NAME) {
            return saveNamePost(message, currentPost, chatId);
        }

        if (currentState == PostState.TEXT) {
            return saveTextPost(message, chatId, currentPost);
        }


        return Sender.sendMessage(
                chatId,
                "Что-то пошло не так. Попробуйте создать пост сначала!",
                ReplayInit.defaultKey());
    }

    private SendMessage saveNamePost(Message message, Post currentPost, Long chatId) {
        currentPost.setPostName(message.getText());
        UserData.currentStatePost.put(chatId, currentPost);
        UserData.currentStateUser.put(chatId, PostState.TEXT);
        return Sender.sendMessage(
                chatId,
                "Придумайте текст поста. Так же вы можете прикрепить фото, видео.",
                null
        );
    }

    private SendMessage saveTextPost(Message message, Long chatId, Post currentPost) {
        String text;

        if (message.getMediaGroupId() != null)
            return Sender.sendMessage(
                    chatId,
                    "Прикрепить можно только одно фото, видео или анимацию.\nОтправьте повторно данные, которые должны быть в посте.",
                    null);

        if (message.hasText()) {
            text = message.getText();
            currentPost.setPostType(PostType.TEXT);
            currentPost.setPostText(text);
        } else if (message.hasPhoto()) {
            text = message.getCaption();

            if (text != null)
                currentPost.setPostText(text);

            currentPost.setPostType(PostType.PHOTO);
            currentPost.setFileId(message.getPhoto().get(message.getPhoto().size() - 1).getFileId());
        } else if (message.hasVideo()) {
            text = message.getCaption();

            if (text != null)
                currentPost.setPostText(text);

            currentPost.setPostType(PostType.VIDEO);
            currentPost.setFileId(message.getVideo().getFileId());
        } else if (message.hasAnimation()) {
            text = message.getCaption();
            if (text != null)
                currentPost.setPostText(text);

            currentPost.setPostType(PostType.ANIMATION);
            currentPost.setFileId(message.getAnimation().getFileId());
        }

        UserData.clear(chatId);
        postService.save(currentPost);
        return Sender.sendMessage(
                chatId,
                "Пост сохранен. Если хотите чтобы он отправлялся, сделайте его активным. Для этого перейдите в список постов.",
                ReplayInit.defaultKey()
        );
    }
}
