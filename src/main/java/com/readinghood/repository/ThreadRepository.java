/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readinghood.repository;

import com.readinghood.entity.Thread;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Long> {
                    
    @Override
    List<Thread> findAll();
    Thread findById(Long id);
    List<Thread> findByOrderByIdDesc();
    List<Thread> findByOrderByViewsDesc();
    List<Thread> findByTitleContaining(String title);
    
}