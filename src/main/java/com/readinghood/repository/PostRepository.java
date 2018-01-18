/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readinghood.repository;

import com.readinghood.entity.Post;
import com.readinghood.entity.Profile;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
                    
    @Override
    List<Post> findAll();
    Post findById(Long id);
    List<Post> findByOrderByIdDesc();
    List<Post> findByAuthor(Profile profile);
    List<Post> findByThread(Thread thr);
    List<Post> findByTextContaining(String text);
  
    
}