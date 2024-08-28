package ru.eddyz.telegramposterbrinkman.services.impl;

import org.springframework.stereotype.Component;
import ru.eddyz.telegramposterbrinkman.entities.Post;
import ru.eddyz.telegramposterbrinkman.repositories.PostRepository;
import ru.eddyz.telegramposterbrinkman.services.PostService;

import java.util.List;
import java.util.Optional;



@Component
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Optional<Post> findByStatus(boolean status) {
        return postRepository.findByStatus(status);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }
}
