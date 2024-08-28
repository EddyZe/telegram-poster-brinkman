package ru.eddyz.telegramposterbrinkman.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.eddyz.telegramposterbrinkman.entities.Channel;
import ru.eddyz.telegramposterbrinkman.repositories.ChannelRepository;
import ru.eddyz.telegramposterbrinkman.services.ChannelService;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;

    @Override
    public void save(Channel channel) {
        if (channelRepository.findByChannelId(channel.getChannelId()).isEmpty())
            channelRepository.save(channel);
    }

    @Override
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }
}
