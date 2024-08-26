package ru.eddyz.telegramposterbrinkman.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.eddyz.telegramposterbrinkman.entities.Channel;
import ru.eddyz.telegramposterbrinkman.repositories.ChannelRepository;
import ru.eddyz.telegramposterbrinkman.services.ChannelService;


@Component
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    @Override
    public void save(Channel channel) {
        channelRepository.save(channel);
    }
}
