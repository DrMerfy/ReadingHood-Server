package com.readinghood.web;

import com.readinghood.entity.Account;
import com.readinghood.entity.Profile;
import com.readinghood.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.readinghood.repository.AccountRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: Server Team
 * @since: 17/1/2018
 * @version: 0.1
 */

@Controller
public class MainController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
      Creates new profile and connects it automatically with an account
     */
    @GetMapping(path = "/register")
    public @ResponseBody
    String addNewUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "surname", required = false) String surname,
            @RequestParam(value = "department", required = false) String department) {

        if (accountRepository.findByEmail(email) == null) {

            String hashedPassword = passwordEncoder.encode(password);

            Account account = new Account();
           
            account.setEmail(email);
            account.setPassword(hashedPassword);
            
            if (role != null) {
                Role accountRole = Role.newRole(role);
                if (accountRole == null) {
                    return "Unsupported account role '" + role + "'";
                }
                account.setAccountRole(accountRole);
            } else {
                account.setAccountRole(Role.USER);
            }

            Profile profile = new Profile(name,username,surname,department,account);
            account.setProfile(profile);
            accountRepository.save(account);

            return "OK";
            
        } else {
            return "Email already exists :(";
        }

    }

    /*
        API page
     */
    @RequestMapping(path = "/api", produces = MediaType.TEXT_HTML_VALUE)
    public @ResponseBody
    String apiPage() {

        String html = null;
        byte[] encoded;
        try {
            encoded = Files.readAllBytes(Paths.get("api.html"));
               html = new String(encoded);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return html;

    }
    
    
    /*
        Verify page
     */
    @RequestMapping(path = "/verify")
    public @ResponseBody
    String afterLoginPage() {
        return "OK";
    }
    
    /*
        Home page
     */
    @RequestMapping(path = "/")
    public @ResponseBody
    String homePage() {

        return "<p style=\"position: absolute; top: 50%; left: 50%; transform: translateX(-50%) translateY(-50%);\">Welcome to ReadingHood Server :)</p>";

    }

}
