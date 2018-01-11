package com.readinghood.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="Tags",indexes = {@Index(columnList = "tag_id"),@Index(columnList = "views"),@Index(columnList = "name")})
public class Tag {

//tag_id (PK)
//views
//threads (1:N)

    @Id
    @Column(name="tag_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="tag_views")
    @NotNull
    private  int views;

    @Column(name = "tag_id")
    private String name;

    @ManyToMany
    @JoinTable(name="Thread_Post",
            joinColumns ={@JoinColumn(name="tag_id")},
            inverseJoinColumns = {@JoinColumn(name="thread_id")})
    List<Thread> threads;

    public Tag(){
        this.views=0;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id=id;
    }

    public int getViews(){
        return views;
    }

    public void setViews(int views){
        this.views=views;
    }

    public String getName(){
        return name;
    }

    public void  setName(String name){
        this.name=name;
    }

}