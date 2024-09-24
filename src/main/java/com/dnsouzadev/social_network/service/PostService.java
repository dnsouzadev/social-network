package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.domain.enums.TypeAccount;
import com.dnsouzadev.social_network.dto.CreatePostDto;
import com.dnsouzadev.social_network.dto.PostDto;
import com.dnsouzadev.social_network.helper.FriendshipUtil;
import com.dnsouzadev.social_network.domain.model.Post;
import com.dnsouzadev.social_network.domain.model.User;
import com.dnsouzadev.social_network.helper.Mapper;
import com.dnsouzadev.social_network.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper mapper;

    @Autowired
    private FriendshipUtil friendshipUtil;

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void createPost(String username, CreatePostDto content) {
        User user = userService.findByUsername(username);
        Post post = new Post(content.content(), user);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<PostDto> listPostsByUser(User user) {
        List<Post> listPosts = postRepository.findByUser(user);
        List<PostDto> listPostDto = new ArrayList<>();
        for (Post post : listPosts) {
            listPostDto.add(mapper.toPostDto(post));
        }
        return listPostDto;
    }

    @Transactional
    public void deletePost(String username, Long id) {
        User user = userService.findByUsername(username);
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

    @Transactional(readOnly = true)
    public PostDto getPost(String username, Long id) {
        User user = userService.findByUsername(username);
        Post post = postRepository.findPostById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (post.getUser().getTypeAccount().equals(TypeAccount.PUBLIC)) {
            return mapper.toPostDto(post);
        } else if (friendshipUtil != null && friendshipUtil.checkFriendship(username, post.getUser().getUsername())) {
            return mapper.toPostDto(post);
        } else if (post.getUser().equals(user)) {
            return mapper.toPostDto(post);
        } else {
            throw new RuntimeException("User not allowed to get this post");
        }
    }

    @Transactional
    public void updatePost(String username, Long id, CreatePostDto content) {
        User user = userService.findByUsername(username);
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
}
