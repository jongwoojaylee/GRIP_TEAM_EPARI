package org.example.grip_demo.search.infrastructure;

import org.example.grip_demo.post.domain.Post;
import org.example.grip_demo.post.domain.PostRepository;
import org.example.grip_demo.post.infrastructure.PostRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public interface SearchPostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

}
