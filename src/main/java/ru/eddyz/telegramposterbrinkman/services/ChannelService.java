package ru.eddyz.telegramposterbrinkman.services;

import ru.eddyz.telegramposterbrinkman.entities.Channel;

import java.util.List;

public interface ChannelService {

    void save(Channel channel);

    List<Channel> findAll();
}
