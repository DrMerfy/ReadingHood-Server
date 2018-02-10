/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readinghood.web;

import com.readinghood.entity.Post;
import com.readinghood.entity.Profile;
import com.readinghood.entity.Tag;
import com.readinghood.entity.Thread;
import com.readinghood.repository.AccountRepository;
import com.readinghood.repository.PostRepository;
import com.readinghood.repository.ProfileRepository;
import com.readinghood.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.readinghood.repository.ThreadRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: Server Team
 * @since: 17/1/2018
 * @version: 0.1
 */
@Controller
@RequestMapping(path = "/threads")
public class ThreadController {

    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private TagRepository tagRepository;

    /*
      Returns all the threads
     */
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Thread> getAllThreads() {
        return threadRepository.findAll();
    }

    /*
      Returns thread with the given id
     */
    @GetMapping(path = "/searchById")
    public @ResponseBody
    Thread getThreadById(@RequestParam Long id) {
        return threadRepository.findById(id);
    }

    /*
       Returns the threads that contain the given text
     */
    @GetMapping(path = "/search")
    public @ResponseBody
    List<Thread> getThreads(@RequestParam String text) {
        Set<Thread> threads = new HashSet<>();
        List<Post> posts = postRepository.findByTextContaining(text);

        // add the threads that have a post that contains the text in them
        for (Post post : posts) {
            threads.add(post.getThread());
        }

        // add the threads that contain the text in their title
        List<Thread> threadsWithTitle = threadRepository.findByTitleContaining(text);
        threads.addAll(threadsWithTitle);
        
        // Set to List
        List<Thread> threadsList = new ArrayList<>();
        threadsList.addAll(threads);
        
        // sort by views
        Collections.sort(threadsList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Thread) o1).getViews() - ((Thread) o2).getViews();
            }
        });
        Collections.reverse(threadsList);
        

        return threadsList;
    }

    /*
      Returns the threads ordered by most recent
     */
    @GetMapping(path = "/recent")
    public @ResponseBody
    List<Thread> getRecentThreads() {
        return threadRepository.findByOrderByIdDesc();
    }

    /*
      Returns the threads ordered by most popular
     */
    @GetMapping(path = "/popular")
    public @ResponseBody
    List<Thread> getPopularThreads() {
        return threadRepository.findByOrderByViewsDesc();
    }

    /*
      Increaces by one the views of thread with the given id
     */
    @GetMapping(path = "/viewed")
    public @ResponseBody
    String increaseViewsOfThread(@RequestParam Long id) {

        Thread thread = threadRepository.findById(id);
        if (thread == null) {
            return "Thread not found";
        }

        thread.increaseViews();
        threadRepository.save(thread);

        return "OK";
    }

    /*
      Adds the thread with the given id to favorites of the connected user 
     */
    @GetMapping(path = "/favorite")
    public @ResponseBody
    String makeFavorite(@RequestParam Long thread_id) {

        Thread thread = threadRepository.findById(thread_id);
        if (thread == null) {
            return "Thread not found";
        }

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile author = accountRepository.findByEmailIgnoreCase(currentUserEmail).getProfile();

        if (author.hasFavorited(thread)){
            return "User has already favorited this thread";
        }
        
        author.addFavoriteThread(thread);
        thread.addFavorer(author);

        profileRepository.save(author);

        return "OK";

    }

    /*
      Returns the threads created by the profile with the given id
     */
    @GetMapping(path = "/created")
    public @ResponseBody
    List<Thread> getCreated(@RequestParam Long profile_id) {
               
        Profile profile = profileRepository.findById(profile_id);        
        List<Post> createdPosts = profile.getCreatedPosts();

        List<Thread> createdThreads = new ArrayList<>();
        createdPosts.forEach((Post post) -> {
            if (post.getIsQuestion()) {
                createdThreads.add(post.getThread());
            }
        });

        Collections.reverse(createdThreads);
        return createdThreads;
    }

    /*
      Returns the threads created by users from the same department as the connected user
     */
    @GetMapping(path = "/sameDepartment")
    public @ResponseBody
    List<Thread> getSameDepartment() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile user = accountRepository.findByEmailIgnoreCase(currentUserEmail).getProfile();

        String myDepartment = user.getDepartment();
        
        List<Thread> allThreads = threadRepository.findAll();
        List<Thread> sameDepartmentThreads = new ArrayList<>();

        allThreads.forEach((Thread thread) -> {
            if (myDepartment.equals(thread.getQuestion().getAuthor().getDepartment())) {
                sameDepartmentThreads.add(thread);
            }
        });

        Collections.reverse(sameDepartmentThreads);
        return sameDepartmentThreads;
    }


    /*
      Returns the threads created by users from the given department
     */
    @GetMapping(path = "/byDepartment")
    public @ResponseBody
    List<Thread> getByDepartment(@RequestParam String department) {
        
        List<Thread> allThreads = threadRepository.findAll();
        List<Thread> sameDepartmentThreads = new ArrayList<>();

        allThreads.forEach((Thread thread) -> {
            if (department.equals(thread.getQuestion().getAuthor().getDepartment())) {
                sameDepartmentThreads.add(thread);
            }
        });

        Collections.reverse(sameDepartmentThreads);
        return sameDepartmentThreads;
    }

    /*
        Creates new thread
     */
    @GetMapping(path = "/new")
    public @ResponseBody
    String addThread(@RequestParam String title, @RequestParam String text,
            @RequestParam(value = "tags", required = false) List<String> tags) {

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile author = accountRepository.findByEmailIgnoreCase(currentUserEmail).getProfile();

        Post post = new Post(author, text);
        Thread thread = new Thread(title, post);
        post.setThread(thread);
        author.addCreatedPost(post);

        if (tags != null) {
            for (String tagName : tags) {
                Tag tag = tagRepository.findByName(tagName);
                if (tag == null) {
                    tag = new Tag(tagName);
                }
                thread.addTag(tag);
                tag.addThread(thread);
            }
        }

        threadRepository.save(thread);

        return "OK";
    }

}
