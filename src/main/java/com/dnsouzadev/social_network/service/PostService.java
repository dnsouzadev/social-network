package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.CommentDto;
import com.dnsouzadev.social_network.dto.CreatePostDto;
import com.dnsouzadev.social_network.dto.LikeDto;
import com.dnsouzadev.social_network.dto.PostDto;
import com.dnsouzadev.social_network.helper.FriendshipUtil;
import com.dnsouzadev.social_network.domain.model.Comment;
import com.dnsouzadev.social_network.domain.model.Like;
import com.dnsouzadev.social_network.domain.model.Post;
import com.dnsouzadev.social_network.domain.model.User;
import com.dnsouzadev.social_network.repository.PostRepository;
import com.dnsouzadev.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipUtil friendshipUtil;

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

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
        } else if (friendshipUtil != null && friendshipUtil.checkFriendship(username, post.getUser().getUsername())) {
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
        return new PostDto(post.getId(), post.getUser().getUsername(), post.getContent(), convertToLikeDto(post.getLikes()), convertToCommentDto(post.getComments()), post.getCreatedAt());
    }

    private Set<LikeDto> convertToLikeDto(Set<Like> likes) {
        return likes.stream().map(like -> new LikeDto(like.getUser().getUsername())).collect(Collectors.toSet());
    }

    private Set<CommentDto> convertToCommentDto(Set<Comment> comments) {
        return comments.stream().map(c -> new CommentDto(c.getId(), c.getUser().getFirstName(), c.getUser().getLastName(), c.getUser().getUsername(), c.getContent(), c.getCreatedAt())).collect(Collectors.toSet());
    }
}
