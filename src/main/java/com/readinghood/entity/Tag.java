package com.readinghood.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="tag",indexes = {@Index(columnList = "tag_id"), @Index(columnList = "tag_name")})
public class Tag {
    
    @Id
    @Column(name="tag_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="tag_usages")
    @NotNull
    private int usages;

    @Column(name = "tag_name")
    @NotNull
    private String name;

    @ManyToMany(mappedBy="tags")
    @JsonBackReference
    List<Thread> threads;

    public Tag(){
        this.usages=0;
        this.threads = new ArrayList<>();
    }
    
    
    public Tag(String name){
        this();
        this.name = name;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public int getUsages(){
        return usages;
    }

    public void setUsages(int usages){
        this.usages=usages;
    }

    public String getName(){
        return name;
    }

    public void  setName(String name){
        this.name=name;
    }
    
    public List<Thread> getThreads(){
        return threads;
    }

    public void  setThreads(List<Thread> threads){
        this.threads=threads;
    }
    
    public void addThread(Thread thread){
        this.threads.add(thread);
    }
    
    public void increaseUsages(){
        this.usages++;
    }

}