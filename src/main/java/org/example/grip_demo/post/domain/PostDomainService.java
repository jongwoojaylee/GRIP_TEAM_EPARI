package org.example.grip_demo.post.domain;

import org.example.grip_demo.post.interfaces.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostDomainService {
    private final PostRepository postRepository;

    @Autowired
    public PostDomainService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    public Page<Post> getAllPostsPageable(Pageable pageable){
        return postRepository.findAll(pageable);
    }


    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}
