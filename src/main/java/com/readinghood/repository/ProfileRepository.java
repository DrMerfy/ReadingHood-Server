/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readinghood.repository;

import com.readinghood.entity.Profile;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface ProfileRepository extends CrudRepository<Profile, Long> {

    @Override
    List<Profile> findAll();

    Profile findById(Long id);

    List<Profile> findByNameStartingWith(String name);
    List<Profile> findBySurnameStartingWith(String surname);
    List<Profile> findByNameStartingWithAndSurnameStartingWith(String name, String surname);
    List<Profile> findByDepartment(String department);
    List<Profile> findByUsername(String username);
    List<Profile> findByUsernameStartingWith(String username);

}
