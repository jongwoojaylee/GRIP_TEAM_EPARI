package org.example.grip_demo.post.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);
    Optional<Post> findById(Long id);
    List<Post> findAll();
    void deleteById(Long id);
    Page<Post> findAll(Pageable pageable);
}
