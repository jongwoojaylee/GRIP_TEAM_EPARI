package org.example.grip_demo.post.application;


import org.example.grip_demo.post.domain.Post;
import org.example.grip_demo.post.domain.PostDomainService;
import org.example.grip_demo.post.domain.PostRepository;
import org.example.grip_demo.post.interfaces.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostDomainService postDomainService;

    @Autowired
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

    public Page<Post> getListPost(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable= PageRequest.of(page,10,Sort.by(sorts));
        return postDomainService.getAllPostsPageable(pageable);
    }

    public Post updatePost(Post post) {
        return postDomainService.updatePost(post);
    }

    public void deletePost(Long id) {
        postDomainService.deletePost(id);
    }



}
