/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readinghood.web;

import com.readinghood.entity.Tag;
import com.readinghood.entity.Thread;
import com.readinghood.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.readinghood.repository.ProfileRepository;
import com.readinghood.repository.TagRepository;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

/**
  @author: Server Team
  @since: 17/1/2018
  @version: 0.1
*/

@Controller
@RequestMapping(path = "/tags")
public class TagController {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AccountRepository accountRepository;;
    @Autowired
    private TagRepository tagRepository;

    /*
      Returns all the existing tags
    */
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Tag> getAllTags() {
        return tagRepository.findAll();
    }
    
    /*
       Returns the tag according to the given id
    */
    @GetMapping(path = "/searchId")
    public @ResponseBody
    Tag getProfileById(@RequestParam Long id) {
        return tagRepository.findById(id);
    }

    /*
        Returns the tag with the given name
    */
    @GetMapping(path = "/searchName")
    public @ResponseBody
    Tag getName(@RequestParam String name) {       
        return tagRepository.findByName(name);
    }

    /*
      Returns the tags that start with the given name
    */
    @GetMapping(path = "/search")
    public @ResponseBody
    List<Tag> getTags(@RequestParam String name) {
        return tagRepository.findByNameStartingWith(name);
    }

    /*
       Returns the views of tag with the given id
    */
    @GetMapping(path = "/views")
    public @ResponseBody
    String getViews(@RequestParam Long id) {

        Tag tag = tagRepository.findById(id);

        if(tag == null) {
          return "Tag Not Found";
        }

        return String.valueOf(tag.getUsages());
    }

    /*
      Returns the tags order by most used
    */
    @GetMapping(path = "/mostUsed")
    public @ResponseBody
    List<Tag> getMostUsedTags() {
        return tagRepository.findByOrderByUsagesDesc();
    }

    /*
      Returns the threads that use the tag with the given name
    */
    @GetMapping(path = "/threads")
    public @ResponseBody
    List<Thread> getThreads(@RequestParam String name) {
        Tag tag = tagRepository.findByName(name);
        
        if(tag == null) {
            return null;
        }
        
        return tag.getThreads();
    }
    
    
    /*
      Creates new tag
    */
    @GetMapping(path = "/new")
    public @ResponseBody
    String createTag(@RequestParam String name) {
        
        if(tagRepository.findByName(name) != null) {
            return "Tag already exists";          
        }
        
        Tag tag = new Tag(name);
        
        tagRepository.save(tag);
        return "OK";
        
    }


    
}
