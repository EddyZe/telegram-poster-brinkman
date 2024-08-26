package ru.eddyz.telegramposterbrinkman.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eddyz.telegramposterbrinkman.entities.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

}
