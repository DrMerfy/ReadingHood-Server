package com.readinghood.entity;

import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "post", indexes = { @Index(columnList = "post_id"), @Index(columnList = "post_timestamp"), @Index(columnList = "post_text") })

public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "post_timestamp")
    private Instant timestamp;

    @Column(name = "post_text")
    private String text;

    @ManyToOne(targetEntity = Profile.class)
    @JoinColumn (name = "profile_id")
    private Profile author;

    
    
    @ManyToMany (mappedBy = "profile")
    private List<Profile> favoredBy;
    
    @ManyToMany (mappedBy = "profile")
    private List<Profile> upvotedBy;
    
    @ManyToMany(mappedBy = "profile")
    private List<Profile> downvotedBy;
    

    @ManyToOne(optional = false)
    private List<Post> edits;

    public Post() {

    }
    
    public Post(String aText, Instant aTimestamp) {
    	text = aText;
    	timestamp = aTimestamp;
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

    public String printText() {
	return this.text;
    }

    public void setAuthor(Profile author) {
	this.author = author;
    }
    
    public Profile getAuthor() {
    	return this.author;
    }


    public void edit(Post edit) {
	this.edits.add(edit);
    }

    public List<Post> getEdits() {
	return this.edits;
    }
    
    public List<Profile> getDownvoters(){
    	return downvotedBy;
    }
    
    public void setDownvoters(List<Profile> downvoters) {
    	this.upvotedBy = downvoters;
    }
    
    public List<Profile> getUpvoters(){
    	return upvotedBy;
    }
    
    public void setUpvoters(List<Profile> upvoters) {
    	this.upvotedBy = upvoters;
    }
    
    public List<Profile> getListOfFavorers(){
    	return favoredBy;
    }
    
    public void setListOfFavorers(List<Profile> favorers) {
    	this.favoredBy = favorers;
    }
    
    public void downvoted(Profile aProfile) {
    	this.downvotedBy.add(aProfile);
    }
    
    public void upvoted(Profile aProfile) {
    	this.upvotedBy.add(aProfile);
    }
    
    public void favorited(Profile aProfile) {
    	this.favoredBy.add(aProfile);
    }

}