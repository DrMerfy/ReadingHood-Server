package com.readinghood.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "post", indexes = { @Index(columnList = "post_id"), @Index(columnList = "post_text") })

public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*
    @Column(name = "post_timestamp")
    private Instant timestamp;
    */

    @Column(name = "post_text")
    @NotNull
    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Profile author;
    
    
    @ManyToOne(targetEntity = Thread.class)
    @JsonBackReference
    private Thread thread;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "upvotes", joinColumns = { @JoinColumn(referencedColumnName = "post_id") }, inverseJoinColumns = { @JoinColumn(referencedColumnName = "profile_id") })
    @JsonManagedReference
    private List<Profile> upvoters;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "downvotes", joinColumns = { @JoinColumn(referencedColumnName = "post_id") }, inverseJoinColumns = { @JoinColumn(referencedColumnName = "profile_id") })
    @JsonManagedReference
    private List<Profile> downvoters;
    

    /*
    @ManyToOne
    private Post editedPost;
    @OneToMany(mappedBy="editedPost")
    private List<Post> edits;
    */

    public Post(){
        this.upvoters = new ArrayList<>();
        this.downvoters = new ArrayList<>();        
    }
    
    public Post(Profile author, String text) {
        this();
        this.author = author;
        this.text = text;
    }

    public void setId(Long Id) {
	this.id = Id;
    }

    public Long getId() {
	return this.id;
    }

    public void setText(String text) {
	this.text = text;
    }

    public String getText() {
	return this.text;
    }

    public void setAuthor(Profile author) {
	this.author = author;
    }
    
    public Profile getAuthor() {
    	return this.author;
    }
    
    public void setThread(Thread thread) {
	this.thread = thread;
    }
    
    public Thread getThread() {
    	return this.thread;
    }

    /*
    public void edit(Post edit) {
	this.edits.add(edit);
    }

    public List<Post> getEdits() {
	return this.edits;
    }*/
    
    public List<Profile> getDownvoters(){
    	return downvoters;
    }
    
    public void setDownvoters(List<Profile> downvoters) {
    	this.upvoters = downvoters;
    }
    
    public List<Profile> getUpvoters(){
    	return upvoters;
    }
    
    public void setUpvoters(List<Profile> upvoters) {
    	this.upvoters = upvoters;
    }
    
    
    public void downvoted(Profile aProfile) {
    	this.downvoters.add(aProfile);
    }
    
    public void upvoted(Profile aProfile) {
    	this.upvoters.add(aProfile);
    }
    
    public void downvotedRemove(Profile aProfile) {
    	this.downvoters.remove(aProfile);
    }
    
    public void upvotedRemove(Profile aProfile) {
    	this.upvoters.remove(aProfile);
    }
    
    
    public int getNumberOfVotes(){
        return upvoters.size() - downvoters.size();
    }

}
