/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readinghood.web;

import com.readinghood.entity.Post;
import com.readinghood.entity.Profile;
import com.readinghood.entity.Thread;
import com.readinghood.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.readinghood.repository.PostRepository;
import com.readinghood.repository.ThreadRepository;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: Server Team
 * @since: 17/1/2018
 * @version: 0.1
 */
@Controller
@RequestMapping(path = "/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private AccountRepository accountRepository;


    /*
      Returns all posts
     */
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }

    /*
      Returns the post with the given id
     */
    @GetMapping(path = "/searchId")
    public @ResponseBody
    Post getPostById(@RequestParam Long id) {
        return postRepository.findById(id);
    }

    /*
      Returns all the posts ordered by most recent 
     */
    @GetMapping(path = "/recent")
    public @ResponseBody
    List<Post> getRecentPosts() {
        return postRepository.findByOrderByIdDesc();
    }

    /*
      Upvotes the post with the given id
     */
    @GetMapping(path = "/upvote")
    public @ResponseBody
    String addUpvote(@RequestParam Long post_id) {

        Post post = postRepository.findById(post_id);
        if (post == null) {
            return "Post not found";
        }

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile upvoter = accountRepository.findByEmail(currentUserEmail).getProfile();

        if (upvoter.hasUpvoted(post)) {
            return "User already upvoted this post";
        }
        if (upvoter.hasDownvoted(post)) {
            post.downvotedRemove(upvoter);
        }
        post.upvoted(upvoter);
        upvoter.addUpvote(post);
        postRepository.save(post);

        return "OK";
    }

    /*
      Downvotes the post with the given id
     */
    @GetMapping(path = "/downvote")
    public @ResponseBody
    String addDownvote(@RequestParam Long post_id) {

        Post post = postRepository.findById(post_id);
        if (post == null) {
            return "Post not found";
        }

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile downvoter = accountRepository.findByEmail(currentUserEmail).getProfile();

        if (downvoter.hasDownvoted(post)) {
            return "User already downvoted this post";
        }
        if (downvoter.hasUpvoted(post)) {
            post.upvotedRemove(downvoter);
        }

        post.downvoted(downvoter);
        downvoter.addDownvote(post);
        postRepository.save(post);

        return "OK";
    }

    /*
      Returns the number of votes of the post with the given id
     */
    @GetMapping(path = "/numberOfVotes")
    public @ResponseBody
    String getNumberOfVotes(@RequestParam Long post_id) {

        Post post = postRepository.findById(post_id);
        if (post == null) {
            return "Post not found";
        }

        return String.valueOf(post.getNumberOfPosts());

    }

    /*
      Creates post in specific thread with the given thread id and the given text<
     */
    @GetMapping(path = "/new")
    public @ResponseBody
    String addPost(@RequestParam Long thread_id, @RequestParam String text) {
        Thread thread = threadRepository.findById(thread_id);
        if (thread == null) {
            return "Thread not found";
        }

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile author = accountRepository.findByEmail(currentUserEmail).getProfile();

        Post post = new Post(author, text);
        thread.addPost(post);
        author.addCreatedPost(post);

        threadRepository.save(thread);

        return "OK";
    }

}
