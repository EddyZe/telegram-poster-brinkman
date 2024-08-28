package ru.eddyz.telegramposterbrinkman.services;

import ru.eddyz.telegramposterbrinkman.entities.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    void save(Post post);
    void deleteById(Long id);
    Optional<Post> findById(Long id);
    Optional<Post> findByStatus(boolean status);

    List<Post> findAll();
}
