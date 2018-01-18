/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readinghood.web;

import com.readinghood.entity.Profile;
import com.readinghood.entity.Thread;
import com.readinghood.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.readinghood.repository.ProfileRepository;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;

/**
  @author: Server Team
  @since: 17/1/2018
  @version: 0.1
*/

@Controller
@RequestMapping(path = "/profiles")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AccountRepository accountRepository;

    /*
      Returns all the profiles
    */
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    /*
      Searches profile with the given id
    */
    @GetMapping(path = "/searchId")
    public @ResponseBody
    Profile getProfileById(@RequestParam Long id) {
        return profileRepository.findById(id);
    }

    /*
      Searches account according to the given name and surname
    */
    @GetMapping(path = "/search")
    public @ResponseBody
    List<Profile> getProfile(@RequestParam String name, @RequestParam String surname) {
        return profileRepository.findByNameStartingWithAndSurnameStartingWith(name,surname);
    }

    /*
      Searches account according to the given name
    */
    @GetMapping(path = "/searchName")
    public @ResponseBody
    List<Profile> getProfileByName(@RequestParam String name) {
        return profileRepository.findByNameStartingWith(name);
    }

    /*
      Searches account according to the given surname
    */
    @GetMapping(path = "/searchSurname")
    public @ResponseBody
    List<Profile> getProfileBySurname(@RequestParam String surname) {
        return profileRepository.findBySurnameStartingWith(surname);
    }

    /*
      Searches account according to the given department
    */
    @GetMapping(path = "/searchDepartment")
    public @ResponseBody
    List<Profile> getProfileByDepartment(@RequestParam String department) {
        return profileRepository.findByDepartment(department);
    }

    /*
      Returns the favorite threads of connected user
    */
    @GetMapping(path = "/favorites")
    public @ResponseBody
    List<Thread> getFavorites() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile user = accountRepository.findByEmail(currentUserEmail).getProfile();
        return user.getFavoriteThreads();
    }

    /*
      Changes the name of connected user
    */
    @GetMapping(path = "/editName")
    public @ResponseBody
    String editName(@RequestParam String name) {


        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile user = accountRepository.findByEmail(currentUserEmail).getProfile();

        if(name.length() == 0){
            return "Name must have at least one character";
        }

        user.setName(name);
        profileRepository.save(user);
        return "OK";

    }

    /*
      Changes the surname of connected user
    */
    @GetMapping(path = "/editSurname")
    public @ResponseBody
    String editSurname(@RequestParam String surname) {


        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile user = accountRepository.findByEmail(currentUserEmail).getProfile();

        if(surname.length() == 0){
            return "Name must have at least one character";
        }

        user.setName(surname);
        profileRepository.save(user);
        return "OK";

    }


}
