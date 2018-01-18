/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readinghood.repository;


import com.readinghood.entity.Tag;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
                    
    @Override
    List<Tag> findAll();
    Tag findById(Long id);
    Tag findByName(String name);
    List<Tag> findByNameStartingWith(String name);
    List<Tag> findByOrderByUsagesDesc();
  
    
}