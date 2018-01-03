/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readinghood.repository;

import com.readinghood.entity.Account;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AccountRepository extends CrudRepository<Account, Long> {
                    
    @Override
    List<Account> findAll();
    List<Account> findAccountByName(String name);
    Account findAccountByEmail(String email);
    List<Account> findAccountBySurname(String surname);
    List<Account> findAccountByNameLike(String name);
    List<Account> findAccountByNameAndSurname(String name,String surname);
    Account findAccountByEmailAndPassword(String email , String password);
    Account findAccountByUsername(String username);
    
    
}