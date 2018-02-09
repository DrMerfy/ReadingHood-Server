package com.readinghood.web;

import com.readinghood.entity.Account;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.readinghood.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

/**
    @author: Server Team 
    @since: 17/1/2018 
    @version: 0.1
*/

@Controller
@RequestMapping(path = "/accounts") 
public class AccountController {

    @Autowired 
    private AccountRepository accountRepository;

    /*
      Returns all the accounts
    */
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /*
      Returns the data of the account with the given username
    */
    @GetMapping(path = "/searchEmail")
    public @ResponseBody
    Account getAccountByEmail(@RequestParam String email) {
        return accountRepository.findByEmail(email);
    }


}
