/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readinghood.web;

import com.readinghood.entity.Account;
import com.readinghood.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.readinghood.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller

public class MainController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(path = "/register") // Map ONLY GET Requests
    public @ResponseBody
    String addNewUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "profile", required = false) String name) {

        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        if (accountRepository.findByEmail(email) == null) {

            String hashedPassword = passwordEncoder.encode(password);

            Account n = new Account();
            n.setUsername(username);
            n.setEmail(email);
            n.setPassword(hashedPassword);
            if (role != null) {
                Role accountRole = Role.newRole(role);
                if (accountRole == null) {
                    return "Unsupported account role '" + role + "'";
                }
                n.setAccountRole(accountRole);
            } else {                
                n.setAccountRole(Role.USER);
            }

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
