package ru.eddyz.telegramposterbrinkman.util.handlers.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import ru.eddyz.telegramposterbrinkman.entities.Channel;
import ru.eddyz.telegramposterbrinkman.repositories.ChannelRepository;
import ru.eddyz.telegramposterbrinkman.services.ChannelService;
import ru.eddyz.telegramposterbrinkman.util.handlers.ChatMemberHandler;


@Component
public class ChatMemberHandlerImpl implements ChatMemberHandler {

    private final ChannelService channelService;

    public ChatMemberHandlerImpl(ChannelService channelService) {

        this.channelService = channelService;
    }

    @Override
    public BotApiMethod<?> handle(ChatMemberUpdated chatMember) {
        Long chatId = chatMember.getChat().getId();
        String title = chatMember.getChat().getTitle();
        channelService.save(Channel.builder()
                .title(title)
                .channelId(chatId)
                .build());

        return null;
    }
}
