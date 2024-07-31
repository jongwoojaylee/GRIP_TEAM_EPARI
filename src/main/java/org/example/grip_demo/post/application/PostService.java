package org.example.grip_demo.post.application;


import org.example.grip_demo.post.domain.Post;
import org.example.grip_demo.post.domain.PostDomainService;

import java.util.List;
import java.util.Optional;

public class PostService {
    private final PostDomainService postDomainService;

    public PostService(PostDomainService postDomainService) {
        this.postDomainService = postDomainService;
    }

    public Post createPost(Post post) {
        return postDomainService.createPost(post);
    }

    public Optional<Post> getPostById(Long id) {
        return postDomainService.getPostById(id);
    }

    public List<Post> getAllPosts() {
        return postDomainService.getAllPosts();
    }

    public Post updatePost(Post post) {
        return postDomainService.updatePost(post);
    }

    public void deletePost(Long id) {
        postDomainService.deletePost(id);
    }

}
