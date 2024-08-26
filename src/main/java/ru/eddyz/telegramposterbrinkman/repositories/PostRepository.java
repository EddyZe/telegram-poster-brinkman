package ru.eddyz.telegramposterbrinkman.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eddyz.telegramposterbrinkman.entities.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByStatus(boolean status);
}
