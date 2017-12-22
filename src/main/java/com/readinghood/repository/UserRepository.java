/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readinghood.repository;

import com.readinghood.model.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {
                    
    List<User> findAll();
    List<User> findUserByName(String name);
    List<User> findUserByEmail(String email);
    List<User> findUserBySurname(String surname);
    List<User> findUserByNameLike(String name);
    List<User> findUserByNameAndSurname(String name,String surname);
    User findUserByEmailAndPassword(String email , String password);
    
    
}