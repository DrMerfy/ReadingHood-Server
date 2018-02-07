package com.readinghood.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "thread", indexes = {@Index(columnList = "thread_id"), @Index(columnList = "thread_views"), @Index(columnList = "thread_title")})
public class Thread {

    @Id
    @Column(name="thread_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "thread_title")
    @NotNull
    private String title;
    
    @Column(name = "thread_views")
    @NotNull
    private int views;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "thread_tag", joinColumns = {@JoinColumn(name = "thread_id")}, inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    @JsonManagedReference
    private List<Tag> tags;

    
    @ManyToMany(mappedBy="favoriteThreads")
    @JsonBackReference
    private List<Profile> favoredBy;
    
    
    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Post> posts;
    

    public Thread(){
        
    }
    
    public Thread(String title) {
        this.title = title;
        this.views = 0;
        this.posts = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.favoredBy = new ArrayList<>();
    }
    
    
    public Thread(String title, Post post) {
        this(title);
        addPost(post);
    }
    
    
    public void addPost(Post post){
        this.posts.add(post);
    }
    
    public void addTag(Tag tag){
        tag.increaseUsages();
        this.tags.add(tag);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void increaseViews() {
        this.views++;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
        
    public List<Profile> getfavoredBy(){
    	return favoredBy;
    }
    
    public void setFavoredBy(List<Profile> favorers) {
    	this.favoredBy = favorers;
    }
    
    public void addFavorer(Profile profile){
        this.favoredBy.add(profile);
    }
    
    @JsonIgnore
    public Post getQuestion(){
        return this.posts.get(0);
    }
    
}
