package ru.eddyz.telegramposterbrinkman.services;

import ru.eddyz.telegramposterbrinkman.entities.Post;

public interface PostService {

    void save(Post post);
    void deleteById(Long id);
}
