/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readinghood.web;

import com.readinghood.entity.Account;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.readinghood.repository.AccountRepository;

@Controller

public class MainController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping(path = "/register") // Map ONLY GET Requests
    public @ResponseBody
    String addNewUser(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(value = "username", required=false) String username,
            @RequestParam(value = "name", required=false)  String name,
            @RequestParam(value = "surname", required=false) String surname,
            @RequestParam(value = "roles", required=false) String roles) {
        
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        if (accountRepository.findAccountByEmail(email) == null) {
            Account n = new Account();
            n.setUsername(username);
            n.setName(name);
            n.setSurname(surname);
            n.setPassword(password);
            n.setEmail(email);

            accountRepository.save(n);
            
            return "OK";
        } else {

            return "Email already exists";
        }

    }

    @RequestMapping(path = "/") // This means URL's start with /demo (after Application path)
    public @ResponseBody
    String hello() {
        
        return "Welcome to ReadingHood Server";

    }

    /*    @GetMapping(path = "/login")
    public @ResponseBody
    List<Object> login(@RequestParam String email, @RequestParam String password) {
    
    Account acc = accountRepository.findAccountByEmailAndPassword(email, password);
    List<Object> response = new ArrayList<>();
    if (acc != null) {
    response.add("OK");
    response.add(acc);
    } else {
    response.add("FAIL");
    }
    
    return response;
    
    }*/
}
