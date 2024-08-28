package ru.eddyz.telegramposterbrinkman.commands.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.eddyz.telegramposterbrinkman.commands.ShowPostsCommand;
import ru.eddyz.telegramposterbrinkman.services.PostService;
import ru.eddyz.telegramposterbrinkman.util.Sender;
import ru.eddyz.telegramposterbrinkman.util.TextHelper;
import ru.eddyz.telegramposterbrinkman.util.enums.InlineButtons;
import ru.eddyz.telegramposterbrinkman.util.keyboards.InlineInit;
import ru.eddyz.telegramposterbrinkman.util.keyboards.ReplayInit;


@Component
@Slf4j
public class ShowPostsCommandImpl implements ShowPostsCommand {

    private final AbsSender sender;
    private final PostService postService;

    public ShowPostsCommandImpl(@Lazy AbsSender sender, PostService postService) {
        this.sender = sender;
        this.postService = postService;
    }

    @Override
    public BotApiMethod<?> execute(Message message) {
        Long chatId = message.getChatId();
        var posts = postService.findAll();

        if (posts.isEmpty())
            return Sender.sendMessage(chatId, "Список постов пока-что пуст!", ReplayInit.defaultKey());

        posts.forEach(post -> {
            try {
                switch (post.getPostType()) {
                    case PHOTO -> sender.execute(
                            Sender.sendPhoto(
                                    chatId,
                                    TextHelper.generatePostMessage(post),
                                    post.getFileId(),
                                    InlineInit.postSetting()
                            )
                    );
                    case VIDEO -> sender.execute(
                            Sender.sendVideo(
                                    chatId,
                                    TextHelper.generatePostMessage(post),
                                    post.getFileId(),
                                    InlineInit.postSetting()
                            )
                    );
                    case TEXT -> sender.execute(
                            Sender.sendMessage(
                                    chatId,
                                    TextHelper.generatePostMessage(post),
                                    InlineInit.postSetting()
                            )
                    );
                    case ANIMATION -> sender.execute(
                            Sender.sendAnimation(
                                    chatId,
                                    TextHelper.generatePostMessage(post),
                                    post.getFileId(),
                                    InlineInit.postSetting()
                            )
                    );
                }
            } catch (TelegramApiException e) {
                log.error("Ошибка при выполнении команды ShowPostsCommand: %s".formatted(e.getMessage()));
            }

        });

        return Sender.sendMessage(
                chatId,
                "Нажмите под нужным потом кнопку <b>'%s'</b>, чтобы изменить статус поста. Если статус активен, то пост будет отправляться в группы.\n\n<b>ВНИМАНИЕ!</b> Прошлый активный пост изменит статус, что он не активен автоматически!"
                        .formatted(InlineButtons.SELECT_POST),
                null);
    }
}
