package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.CreatePostDto;
import com.dnsouzadev.social_network.model.Post;
import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.repository.PostRepository;
import com.dnsouzadev.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public void createPost(String username, CreatePostDto content) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post(content.content(), user);
        postRepository.save(post);
    }

    public List<Post> listPostsByUsername(String username) {
        return postRepository.findByUsername(username);
    }

    public List<Post> listAllPosts() {
        return postRepository.findAll();
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}
