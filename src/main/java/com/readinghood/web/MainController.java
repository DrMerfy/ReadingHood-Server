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
            account.setUsername(username);
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

            Profile profile = new Profile();
            profile.setName(name);
            profile.setSurname(surname);
            profile.setDepartment(department);
            profile.setAccount(account);

            account.setProfile(profile);

            accountRepository.save(account);

            return "OK";
            
        } else {
            return "Email already exists :(";
        }

    }

    /*
        Home page
     */
    @RequestMapping(path = "/")
    public @ResponseBody
    String hello() {

        return "CONNECTED - Welcome to ReadingHood Server :)";

    }

}
