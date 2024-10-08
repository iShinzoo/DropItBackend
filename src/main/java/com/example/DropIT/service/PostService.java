package com.example.DropIT.service;

import com.example.DropIT.controller.Request.PostRequest;
import com.example.DropIT.controller.Request.PostUpdateRequest;
import com.example.DropIT.entities.Post;
import com.example.DropIT.entities.User;
import com.example.DropIT.repository.PostRepository;

import com.example.DropIT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(PostRequest request) {

        System.out.println(request.toString());
        // Validate the incoming request
        validatePostRequest(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByEmail(username);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        Post post = new Post();
        post.setFromLocation(request.getFromLocation());
        post.setToLocation(request.getToLocation());
        post.setSender(user);
        post.setItemDescription(request.getItemDescription());
        post.setWeight(request.getWeight());
        post.setPrice(request.getPrice());
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post by id not found")); // Handle post not found
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post updatePost(Long id, PostUpdateRequest request) {
        validatePostUpdateRequest(request);
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with this id: " + id));

        existingPost.setFromLocation(request.getFromLocation());
        existingPost.setToLocation(request.getToLocation());
        existingPost.setItemDescription(request.getItemDescription());
        existingPost.setWeight(request.getWeight());
        existingPost.setPrice(request.getPrice());
        existingPost.setCreatedAt(LocalDateTime.now());
        return postRepository.save(existingPost);
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post not found with this id: " + id);
        }
        postRepository.deleteById(id);
    }
    private void validatePostRequest(PostRequest request) {
        if (request.getFromLocation() == null || request.getToLocation() == null ||
                request.getItemDescription() == null || request.getWeight() == null || request.getPrice() == null) {
            throw new IllegalArgumentException("All fields are required for creating a post");
        }
    }

    private void validatePostUpdateRequest(PostUpdateRequest request) {
        if (request.getFromLocation() == null || request.getToLocation() == null) {
            throw new IllegalArgumentException("From and To locations are required for updating a post");
        }
    }
}
