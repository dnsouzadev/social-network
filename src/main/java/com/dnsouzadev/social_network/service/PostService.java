package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.CreatePostDto;
import com.dnsouzadev.social_network.dto.PostDto;
import com.dnsouzadev.social_network.model.Post;
import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.repository.PostRepository;
import com.dnsouzadev.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<PostDto> listPostsByUser(User user) {
        List<Post> listPosts = postRepository.findByUser(user);
        List<PostDto> listPostDto = new ArrayList<>();
        for (Post post : listPosts) {
            listPostDto.add(new PostDto(post.getId(), post.getContent(), post.getLikes(), post.getComments(), post.getCreatedAt()));
        }
        return listPostDto;
    }

    public List<Post> listAllPosts() {
        return postRepository.findAll();
    }

    public void deletePost(String username, Long id) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        postRepository.findPostById(id)
                .ifPresentOrElse(post -> {
                    if (post.getUser().equals(user)) {
                        postRepository.delete(post);
                    } else {
                        throw new RuntimeException("User not allowed to delete this post");
                    }
                }, () -> {
                    throw new RuntimeException("Post not found");
                });
    }

}