package ru.eddyz.telegramposterbrinkman.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eddyz.telegramposterbrinkman.entities.Channel;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Optional<Channel> findByChannelId(Long channelId);
}
