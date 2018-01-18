/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readinghood.repository;

import com.readinghood.entity.Account;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
                    
    @Override
    List<Account> findAll();
    Account findByEmail(String email);
    List<Account> findByUsername(String username);
    
}