package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.CreatePostDto;
import com.dnsouzadev.social_network.dto.PostDto;
import com.dnsouzadev.social_network.helper.CheckFriendship;
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

    @Autowired
    private CheckFriendship checkFriendship;

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
            listPostDto.add(convertToDto(post));
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

    public PostDto getPost(String username, Long id) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findPostById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (post.getUser().getTypeAccount().equals("PUBLIC")) {
            return convertToDto(post);
        } else if (checkFriendship != null && checkFriendship.check(username, post.getUser().getUsername())) {
            return convertToDto(post);
        } else if (post.getUser().equals(user)) {
            return convertToDto(post);
        } else {
            throw new RuntimeException("User not allowed to get this post");
        }
    }

    public void updatePost(String username, Long id, CreatePostDto content) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        postRepository.findPostById(id)
                .ifPresentOrElse(post -> {
                    if (post.getUser().equals(user)) {
                        post.setContent(content.content());
                        postRepository.save(post);
                    } else {
                        throw new RuntimeException("User not allowed to update this post");
                    }
                }, () -> {
                    throw new RuntimeException("Post not found");
                });
    }

    private PostDto convertToDto(Post post) {
        return new PostDto(post.getId(), post.getUser().getUsername(), post.getContent(), post.getLikes(), post.getComments(), post.getCreatedAt());
    }
}
